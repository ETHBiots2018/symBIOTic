package eth.biots;

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

public class Main {

    public static void main(String[] args) {
	// write your code here
        String address = "0x5ba28f28aa3113df222858ad69f9b618f2b5bcf0";
        String password = "12345678";
        String wallet = "./wallet.json";

        //initialise web3j
        Web3j web3 = Web3j.build(new InfuraHttpService("https://ropsten.infura.io/yjAnYyZgUECNLVLUPpGK"));
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion);

        //load credentials
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(password, wallet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }

//        create
//        YourSmartContract contract = YourSmartContract.deploy(
//                <web3j>, <credentials>,
//                GAS_PRICE, GAS_LIMIT,
//        <param1>, ..., <paramN>).send();  // constructor params

        //load contract from Ethereum network (has to be defined locally also)
        Token contract = Token.load(
                "0xdf208fa545a197aac95bc0874f00f4609fbafa11",
                web3,
                credentials,
                new BigInteger("100"),
                new BigInteger("10000000")
        );

        if (false){
            //transfer funds
            try {
                TransactionReceipt transactionReceipt = Transfer.sendFunds(
                        web3, credentials, address,
                        BigDecimal.valueOf(1.0), Convert.Unit.ETHER)
                        .send();
                System.out.println(transactionReceipt.getBlockHash());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //create new address
            try {
                String walletFileName = WalletUtils.generateFullNewWalletFile(password,new File("./"));
                String[] fetchAddress=walletFileName.split("--");
                String getAddress = fetchAddress[fetchAddress.length-1].split("\\.")[0];
                System.out.println(getAddress);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //interact with contract
        try {
            System.out.println(contract.balanceOf(address).send().intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
