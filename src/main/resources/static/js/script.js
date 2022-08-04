console.log("This is script file")

const ethereumButton = document.querySelector('.enableEthereumButton');
const showAccount = document.querySelector('.showAccount');

ethereumButton.addEventListener('click', () => {
    getAccount();
});

async function getAccount() {
    const accounts = await ethereum.request({method: 'eth_requestAccounts'});
    const ethAccount = accounts[0];
    showAccount.innerHTML = ethAccount;

    $.ajax({
        type: "GET",
        url: "/user/connect-wallet/" + ethAccount,
        timeout: 100000,
        success: function (account) {
            console.log("SUCCESS: ", ethAccount);
            display(ethAccount);
            alert(response);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });


}

function showUser(user){
    console.log("User",user);
}