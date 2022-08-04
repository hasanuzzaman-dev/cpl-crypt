package com.hasan.cplcrypt.component;

import com.hasan.cplcrypt.contracts.Transactions;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class RunAfterStartup {

    public static final String PRIVATE_KEY = "6ffb3d9ed03eb56d81c07be18f25ad44428bd2a3220224d45385d219dc09030a";
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    public static final String ETH_ACCOUNT = "0x6371e768C0ee4Dd2966800B6c4469B05d0765308";
    public static String DEPLOY_ADDRESS = "deploy_address";
    public static TransactionManager TRANSACTION_MANAGER;
    public static ContractGasProvider CONTRACT_GAS_PROVIDER;

    public static final String FROM = "0xb0b4bB1586dE2A0Baa59d35fe3e76DC261f25734";
    public static final String RECIPIENT = "0xaD2a26157434a57d16C8f84569563f570c4Ed8e5";
    public static Web3j WEB_3_J;
    //public static final Web3j WEB_3_J = Web3j.build(new HttpService("https://eth-rinkeby.alchemyapi.io/v2/ukXBvGXFSkA7R3alQlK8Qg8qCBvLql3s"));


    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {

        System.out.println("Connecting to Ethereum ...");
        WEB_3_J = Web3j.build(new HttpService("http://localhost:7545"));
        System.out.println("Successfully connected to Ethereum");

       /* try {
            // web3_clientVersion returns the current client version.
            Web3ClientVersion clientVersion = WEB_3_J.web3ClientVersion().send();

            // eth_blockNumber returns the number of most recent block.
            EthBlockNumber blockNumber = WEB_3_J.ethBlockNumber().send();

            // eth_gasPrice, returns the current price per gas in wei.
            EthGasPrice gasPrice = WEB_3_J.ethGasPrice().send();

            //Get balance result synchronously
            EthGetBalance balanceResult = WEB_3_J.ethGetBalance(ETH_ACCOUNT,
                    DefaultBlockParameterName.LATEST).send();

            //Obtain the BigInteger balance representation, in the wei unit.
            BigInteger balanceInWei = balanceResult.getBalance();

            //Obtain the BigDecimal balance representation, in the ETH unit.

            BigDecimal balanceInEther = Convert.fromWei(balanceInWei.toString(), Convert.Unit.ETHER);

            // Print result
            System.out.println("Client version: " + clientVersion.getWeb3ClientVersion());
            System.out.println("Block number: " + blockNumber.getBlockNumber());
            System.out.println("Gas price: " + gasPrice.getGasPrice());
            System.out.println("balanceInWei: " + balanceInWei);
            System.out.println("balanceInEth: " + balanceInEther);

        } catch (IOException ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        }
*/

        Credentials credentials = getCredentialsFromPrivateKey();

        try {
            TRANSACTION_MANAGER = new RawTransactionManager(
                    WEB_3_J,
                    credentials
            );

            CONTRACT_GAS_PROVIDER = new ContractGasProvider() {
                @Override
                public BigInteger getGasPrice(String s) {
                    return GAS_PRICE;
                }

                @Override
                public BigInteger getGasPrice() {
                    return null;
                }

                @Override
                public BigInteger getGasLimit(String s) {
                    return GAS_LIMIT;
                }

                @Override
                public BigInteger getGasLimit() {
                    return null;
                }
            };

            DEPLOY_ADDRESS = deployContract(WEB_3_J, TRANSACTION_MANAGER, CONTRACT_GAS_PROVIDER);
            System.out.println("DeployAddress: " + DEPLOY_ADDRESS);


        } catch (IOException ex) {
            throw new RuntimeException("Error whilst sending json-rpc requests", ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Yaaah, I am running........");
    }


    private String deployContract(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) throws Exception {
        return Transactions
                .deploy(web3j, transactionManager, contractGasProvider)
                .send()
                .getContractAddress();
    }

    private Transactions loadContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return Transactions.load(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    private void transferEthereum(Web3j web3j, TransactionManager transactionManager) throws Exception {
        Transfer transfer = new Transfer(web3j, transactionManager);

        TransactionReceipt transactionReceipt = transfer.sendFunds(
                RECIPIENT,
                BigDecimal.ONE,
                Convert.Unit.ETHER,
                GAS_PRICE,
                GAS_LIMIT
        ).send();

        System.out.print("Transaction = " + transactionReceipt.getTransactionHash());
    }

    private void printWeb3Version(Web3j web3j) {
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3j.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String web3ClientVersionString = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Web3 client version: " + web3ClientVersionString);
    }

    private Credentials getCredentialFromWallet() throws CipherException, IOException {
        return WalletUtils.loadCredentials(
                "passphrase",
                "wallet/path"
        );
    }

    private Credentials getCredentialsFromPrivateKey() {
        return Credentials.create(PRIVATE_KEY);
    }
}
