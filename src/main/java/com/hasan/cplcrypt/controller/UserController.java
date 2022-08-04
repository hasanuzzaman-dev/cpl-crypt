package com.hasan.cplcrypt.controller;

import com.hasan.cplcrypt.contracts.Transactions;
import com.hasan.cplcrypt.models.MyTransaction;
import com.hasan.cplcrypt.models.User;
import com.hasan.cplcrypt.services.UserService;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
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
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.hasan.cplcrypt.component.RunAfterStartup.*;
import static com.hasan.cplcrypt.contracts.Transactions.TRANSFER_EVENT;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/index")
    public String dashBoard(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("USERNAME: " + userName);

        // get the username using username
        User user = userService.getUserByUserName(userName);
        System.out.println("USER: " + user.toString());
        model.addAttribute("user", user);
        model.addAttribute("myTransaction", new MyTransaction());
        model.addAttribute("title", "CPL Crypt");


        return "normal/user_dashboard";
    }

    @RequestMapping(value = "/send-ether", method = RequestMethod.POST)
    public String sendEther(Model model, @ModelAttribute("myTransaction") MyTransaction myTransaction) {
        // model.addAttribute("myTransaction",new MyTransaction());


        return "normal/user_dashboard";
    }


    @GetMapping(value = "/connect-wallet/{ethAccount}")
    public String connectWallet(Model model, Principal principal, @PathVariable(value = "ethAccount") String ethAccount) {

        String userName = principal.getName();

        // get the username using username
        User user = userService.getUserByUserName(userName);

        model.addAttribute("user", user);
        model.addAttribute("myTransaction", new MyTransaction());
        model.addAttribute("title", "CPL Crypt");


        return "normal/user_dashboard";

    }

    @GetMapping(value = "/send-ether")
    public String sendEther(Model model, Principal principal) throws Exception {

        System.out.println("Send Ether");

        String userName = principal.getName();

        // get the username using username
        User user = userService.getUserByUserName(userName);

        model.addAttribute("user", user);
        model.addAttribute("myTransaction", new MyTransaction());
        model.addAttribute("title", "CPL Crypt");

/*

        Transactions transactions = loadContract(DEPLOY_ADDRESS, WEB_3_J, TRANSACTION_MANAGER, CONTRACT_GAS_PROVIDER);


        transactions.addToBlockchain(RECIPIENT, BigInteger.valueOf(1), "Test", "1st Transaction");

        */
/*Flowable<Transactions.TransferEventResponse> responseFlowable = transactions.transferEventFlowable(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST
        );*//*


        String TRANSFER_EVENT_HASH = EventEncoder.encode(TRANSFER_EVENT);
        Transactions.TransferEventResponse transferEventResponse = new Transactions.TransferEventResponse();
        transferEventResponse.from = FROM;
        transferEventResponse.receiver = RECIPIENT;
        transferEventResponse.amount = BigInteger.valueOf(10);
        transferEventResponse.message = "Hello";
        transferEventResponse.timestamp = BigInteger.valueOf(System.currentTimeMillis());
        transferEventResponse.keyword = "Test";

        EthFilter ethFilter = new EthFilter(
                DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST,
                transactions.getContractAddress()
        ).addSingleTopic(TRANSFER_EVENT_HASH);


        System.out.println("DEPLOY_ADDRESS: " + DEPLOY_ADDRESS);
        System.out.println("DEPLOY_ADDRESS: " + transactions.getContractAddress().substring(2));

        Flowable<Transactions.TransferEventResponse> transferEventResponseFlowable = transactions.transferEventFlowable(DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST);
        System.out.println("transferEventResponseFlowable: " + transferEventResponseFlowable);

        Flowable<Transactions.TransferEventResponse> eventResponseFlowable = transactions
                .transferEventFlowable(ethFilter)
                .map(new Function<Transactions.TransferEventResponse, Transactions.TransferEventResponse>() {
                    @Override
                    public Transactions.TransferEventResponse apply(@NotNull Transactions.TransferEventResponse eventResponse) throws Exception {
                        eventResponse = transferEventResponse;
                        return transferEventResponse;
                    }
                });



        WEB_3_J.ethLogFlowable(ethFilter).subscribe(log -> {
            System.out.println("Event log: "+log);
            List<Type> params = FunctionReturnDecoder.decode(log.getData() , TRANSFER_EVENT.getParameters());

            System.out.println("Event param 0: "+params.get(0).getValue());
            transferEventResponse.log = log;
            System.out.println("Event param : "+params);
            System.out.println("Event param amount: "+String.valueOf(params.get(2).getValue()));

        }).dispose();

       */
/* WEB_3_J.ethLogFlowable(ethFilter).subscribe(event -> {
            System.out.println(event);
        });*//*


        RemoteFunctionCall<TransactionReceipt> transactionReceiptRemoteFunctionCall = transactions.addToBlockchain(
                RECIPIENT,
                transferEventResponse.amount,
                transferEventResponse.message,
                transferEventResponse.keyword);

        System.out.println(RECIPIENT + " " + transferEventResponse.amount + " " + transferEventResponse.message + " " + transferEventResponse.keyword);

        transactionReceiptRemoteFunctionCall.send();
        //System.out.println(transactionReceiptRemoteFunctionCall.encodeFunctionCall());
*/

        Transactions transactions = loadContract(DEPLOY_ADDRESS, WEB_3_J, TRANSACTION_MANAGER, CONTRACT_GAS_PROVIDER);


        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = transactions.addToBlockchain(
                RECIPIENT, BigInteger.valueOf(1), "Test", "1st Transaction");

        TransactionReceipt receipt = remoteFunctionCall.send();
        System.out.println("remoteFunctionCall: "+receipt.toString());

        /*// Transfer data from account to another
        Transfer transfer = new Transfer(WEB_3_J, TRANSACTION_MANAGER);

        TransactionReceipt transactionReceipt = transfer.sendFunds(
                RECIPIENT,
                BigDecimal.ONE,
                Convert.Unit.ETHER,
                GAS_PRICE,
                GAS_LIMIT
        ).send();*/


        return "normal/user_dashboard";

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

    private Credentials getCredentialFromWallet() throws CipherException, IOException {
        return WalletUtils.loadCredentials(
                "passphrase",
                "wallet/path"
        );
    }

    private Credentials getCredentialsFromPrivateKey() {
        return Credentials.create(PRIVATE_KEY);
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
}
