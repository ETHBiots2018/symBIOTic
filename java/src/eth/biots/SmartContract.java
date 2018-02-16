package eth.biots;

import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.infura.InfuraHttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class SmartContract {

    public Web3j web3 = Web3j.build(new InfuraHttpService("https://ropsten.infura.io/yjAnYyZgUECNLVLUPpGK"));
    //Android
    //Web3j web3 = Web3jFactory.build(new InfuraHttpService("https://ropsten.infura.io/yjAnYyZgUECNLVLUPpGK"));

    String address = "0x5ba28f28aa3113df222858ad69f9b618f2b5bcf0";
    String contractAddress = "0x43072c60bb645fe46bb86a447f9c27f14e9065f1";
    String password = "12345678";
    String wallet = "./wallet.json";
    Credentials credentials = null;
    Lottery contract;

    public SmartContract() {
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        //load credentials
        try {
            credentials = WalletUtils.loadCredentials(password, wallet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }

        contract = Lottery.load(
                contractAddress,
                web3,
                credentials,
                new BigInteger("10000000000"),
                new BigInteger("1000000")
        );
    }

    public void transferFunds(int ether, String address){
        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3, credentials, address,
                    BigDecimal.valueOf(ether), Convert.Unit.ETHER)
                    .send();
            System.out.println(transactionReceipt.getBlockHash());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createNewAddress(){
        //create new address
        String address = "";
        try {
            String walletFileName = WalletUtils.generateFullNewWalletFile(password, new File("./"));
            String[] fetchAddress=walletFileName.split("--");
            address= fetchAddress[fetchAddress.length-1].split("\\.")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }

    public int getBalance(String address){
        int balance = 0;
        try {
            balance = contract.getBalance(address).send().intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return balance;
    }

    public boolean checkLottery(String address){
        boolean won = false;
        try {
            won = contract.didIWin(address).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return won;
    }

    public void recycle(String address){
        try {
            contract.recycle(address).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
