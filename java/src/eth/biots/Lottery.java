package eth.biots;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class Lottery extends Contract {
    private static final String BINARY = "60606040526005805460a060020a60ff02191681556006556000600781905560085560098054600160a060020a031916905534156200003d57600080fd5b60058054600160a060020a03191633600160a060020a0316179055620000917f300000000000000000000000000000000000000000000000000000000000000064010000000062000097810262002c951704565b62000767565b600054600160a060020a03161580620000d25750600054620000d090600160a060020a031664010000000062000bc2620002c382021704565b155b15620000f457620000f2600064010000000062000bc6620002c782021704565b505b60008054600160a060020a0316906338cc483190604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15156200015757600080fd5b6102c65a03f115156200016957600080fd5b5050506040518051600154600160a060020a039081169116149050620002285760008054600160a060020a0316906338cc483190604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1515620001ec57600080fd5b6102c65a03f11515620001fe57600080fd5b505050604051805160018054600160a060020a031916600160a060020a0392909216919091179055505b600154600160a060020a031663688dcfd7826040517c010000000000000000000000000000000000000000000000000000000063ffffffff84160281527fff000000000000000000000000000000000000000000000000000000000000009091166004820152602401600060405180830381600087803b1515620002ab57600080fd5b6102c65a03f11515620002bd57600080fd5b50505050565b3b90565b600080620002f7731d3b2638a7cc9f2cb3d298a3da7a90b67e5506ed64010000000062000bc2620002c382021704565b1115620003795760008054600160a060020a031916731d3b2638a7cc9f2cb3d298a3da7a90b67e5506ed1790556200037060408051908101604052600b81527f6574685f6d61696e6e65740000000000000000000000000000000000000000006020820152640100000000620019c8620006a982021704565b506001620006a4565b6000620003a873c03a2615d5efaf5f49f60b7bb6583eaec212fdf164010000000062000bc2620002c382021704565b1115620004215760008054600160a060020a03191673c03a2615d5efaf5f49f60b7bb6583eaec212fdf11790556200037060408051908101604052600c81527f6574685f726f707374656e3300000000000000000000000000000000000000006020820152640100000000620019c8620006a982021704565b60006200045073b7a07bcf2ba2f2703b24c0691b5278999c59ac7e64010000000062000bc2620002c382021704565b1115620004c95760008054600160a060020a03191673b7a07bcf2ba2f2703b24c0691b5278999c59ac7e1790556200037060408051908101604052600981527f6574685f6b6f76616e00000000000000000000000000000000000000000000006020820152640100000000620019c8620006a982021704565b6000620004f873146500cfd35b22e4a392fe0adc06de1a1368ed4864010000000062000bc2620002c382021704565b1115620005715760008054600160a060020a03191673146500cfd35b22e4a392fe0adc06de1a1368ed481790556200037060408051908101604052600b81527f6574685f72696e6b6562790000000000000000000000000000000000000000006020820152640100000000620019c8620006a982021704565b6000620005a0736f485c8bf6fc43ea212e93bbf8ce046c7f1cb47564010000000062000bc2620002c382021704565b1115620005d6575060008054600160a060020a031916736f485c8bf6fc43ea212e93bbf8ce046c7f1cb4751790556001620006a4565b6000620006057320e12a1f859b3feae5fb2a0a32c18f5a65555bbf64010000000062000bc2620002c382021704565b11156200063b575060008054600160a060020a0319167320e12a1f859b3feae5fb2a0a32c18f5a65555bbf1790556001620006a4565b60006200066a7351efaf4c8b3c9afbd5ab9f4bbc82784ab6ef8faa64010000000062000bc2620002c382021704565b1115620006a0575060008054600160a060020a0319167351efaf4c8b3c9afbd5ab9f4bbc82784ab6ef8faa1790556001620006a4565b5060005b919050565b6002818051620006be929160200190620006c2565b5050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200070557805160ff191683800117855562000735565b8280016001018555821562000735579182015b828111156200073557825182559160200191906001019062000718565b506200074392915062000747565b5090565b6200076491905b808211156200074357600081556001016200074e565b90565b612e7380620007776000396000f3006060604052600436106100955763ffffffff60e060020a60003504166327dc297e811461009a57806338bbfa50146100f257806359992cc81461018a5780638da5cb5b146101a0578063a9059cbb146101cf578063b2288649146101f1578063b5cc453914610216578063baca00041461021e578063f12be3261461023d578063f2fde38b14610270578063f8b2cb4f1461028f575b600080fd5b34156100a557600080fd5b6100f0600480359060446024803590810190830135806020601f820181900481020160405190810160405281815292919060208401838380828437509496506102ae95505050505050565b005b34156100fd57600080fd5b6100f0600480359060446024803590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506102e095505050505050565b341561019557600080fd5b6100f06004356104fc565b34156101ab57600080fd5b6101b3610553565b604051600160a060020a03909116815260200160405180910390f35b34156101da57600080fd5b6100f0600160a060020a0360043516602435610562565b34156101fc57600080fd5b6102046105d2565b60405190815260200160405180910390f35b6100f0610600565b341561022957600080fd5b6100f0600160a060020a036004351661061d565b341561024857600080fd5b61025c600160a060020a0360043516610713565b604051901515815260200160405180910390f35b341561027b57600080fd5b6100f0600160a060020a0360043516610784565b341561029a57600080fd5b610204600160a060020a0360043516610812565b6102dc828260006040518059106102c25750595b818152601f19601f830116810160200160405290506102e0565b5050565b60008060008060006102f061082d565b600160a060020a031633600160a060020a031614151561030f57600080fd5b61031a8888886109cd565b60ff161561033c5760075460001943014081151561033457fe5b0694506103c2565b670100000000000000935083876040518082805190602001908083835b602083106103785780518252601f199092019160209182019101610359565b6001836020036101000a03801982511681845116179092525050509190910192506040915050519081900390208115156103ae57fe5b069250600754838115156103be57fe5b0694505b5060009050805b60085481101561043b576000818152600a6020908152604080832054600160a060020a03168352600b9091529020549190910190848210610433576000818152600a602052604090205460098054600160a060020a031916600160a060020a039092169190911790555b6001016103c9565b610443610af7565b6005805474ff000000000000000000000000000000000000000019169055600954600160a060020a0316156104f2576009547f5b690ec4a06fe979403046eaeea5b3ce38524683c3001f662c8b5a829632f7df90600160a060020a0316604051600160a060020a03909116815260200160405180910390a1600954600160a060020a03166000670de0b6b3a7640000604051600060405180830381858888f1935050505015156104f257600080fd5b5050505050505050565b60055433600160a060020a0390811691161461051757600080fd5b60008060055474010000000000000000000000000000000000000000900460ff16600181111561054357fe5b1461054d57600080fd5b50600655565b600554600160a060020a031681565b600160a060020a0333166000908152600b60205260409020548190101561058857600080fd5b600160a060020a0333166000908152600b60205260409020805482900390556105b082610b54565b600160a060020a039091166000908152600b6020526040902080549091019055565b60055460009033600160a060020a039081169116146105f057600080fd5b50600160a060020a033016315b90565b60055433600160a060020a0390811691161461061b57600080fd5b565b60008060055474010000000000000000000000000000000000000000900460ff16600181111561064957fe5b1461065357600080fd5b670de0b6b3a7640000600160a060020a03301631101561067257600080fd5b600160a060020a038216151561068757600080fd5b6006546007541061069757600080fd5b6106a082610b54565b600160a060020a0382166000908152600b6020526040902080546001908101909155600780549091019081905560065414156102dc576005805474ff00000000000000000000000000000000000000001916740100000000000000000000000000000000000000001790556102dc610ba9565b600954600090600160a060020a0316604051600160a060020a03919091166c0100000000000000000000000002815260140160405190819003902082604051600160a060020a03919091166c010000000000000000000000000281526014016040519081900390201490505b919050565b60055433600160a060020a0390811691161461079f57600080fd5b600160a060020a03811615156107b457600080fd5b600554600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a360058054600160a060020a031916600160a060020a0392909216919091179055565b600160a060020a03166000908152600b602052604090205490565b60008054600160a060020a03161580610858575060005461085690600160a060020a0316610bc2565b155b15610869576108676000610bc6565b505b60008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b15156108b257600080fd5b6102c65a03f115156108c357600080fd5b5050506040518051600154600160a060020a0390811691161490506109665760008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b151561092b57600080fd5b6102c65a03f1151561093c57600080fd5b505050604051805160018054600160a060020a031916600160a060020a0392909216919091179055505b600154600160a060020a031663c281d19e6000604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b15156109ae57600080fd5b6102c65a03f115156109bf57600080fd5b505050604051805191505090565b600080826000815181106109dd57fe5b016020015160f860020a900460f860020a02600160f860020a0319167f4c00000000000000000000000000000000000000000000000000000000000000141580610a6f575082600181518110610a2f57fe5b016020015160f860020a900460f860020a02600160f860020a0319167f500000000000000000000000000000000000000000000000000000000000000014155b80610ab7575082600281518110610a8257fe5b01602001517fff0000000000000000000000000000000000000000000000000000000000000060f860020a9182900482021614155b15610ac55760019150610aef565b610ad8838686610ad3610eec565b610f94565b9050801515610aea5760029150610aef565b600091505b509392505050565b60005b600854811015610b47576000818152600a602081815260408084208054600160a060020a03168552600b835290842084905592849052528054600160a060020a0319169055600101610afa565b5060006007819055600855565b600160a060020a0381166000908152600b60205260409020541515610ba65760088054600181019091556000908152600a602052604090208054600160a060020a031916600160a060020a0383161790555b50565b6007600062030d40610bbc828483611522565b50505050565b3b90565b600080610be6731d3b2638a7cc9f2cb3d298a3da7a90b67e5506ed610bc2565b1115610c565760008054600160a060020a031916731d3b2638a7cc9f2cb3d298a3da7a90b67e5506ed179055610c4e60408051908101604052600b81527f6574685f6d61696e6e657400000000000000000000000000000000000000000060208201526119c8565b50600161077f565b6000610c7573c03a2615d5efaf5f49f60b7bb6583eaec212fdf1610bc2565b1115610cdd5760008054600160a060020a03191673c03a2615d5efaf5f49f60b7bb6583eaec212fdf1179055610c4e60408051908101604052600c81527f6574685f726f707374656e33000000000000000000000000000000000000000060208201526119c8565b6000610cfc73b7a07bcf2ba2f2703b24c0691b5278999c59ac7e610bc2565b1115610d645760008054600160a060020a03191673b7a07bcf2ba2f2703b24c0691b5278999c59ac7e179055610c4e60408051908101604052600981527f6574685f6b6f76616e000000000000000000000000000000000000000000000060208201526119c8565b6000610d8373146500cfd35b22e4a392fe0adc06de1a1368ed48610bc2565b1115610deb5760008054600160a060020a03191673146500cfd35b22e4a392fe0adc06de1a1368ed48179055610c4e60408051908101604052600b81527f6574685f72696e6b65627900000000000000000000000000000000000000000060208201526119c8565b6000610e0a736f485c8bf6fc43ea212e93bbf8ce046c7f1cb475610bc2565b1115610e3e575060008054600160a060020a031916736f485c8bf6fc43ea212e93bbf8ce046c7f1cb475179055600161077f565b6000610e5d7320e12a1f859b3feae5fb2a0a32c18f5a65555bbf610bc2565b1115610e91575060008054600160a060020a0319167320e12a1f859b3feae5fb2a0a32c18f5a65555bbf179055600161077f565b6000610eb07351efaf4c8b3c9afbd5ab9f4bbc82784ab6ef8faa610bc2565b1115610ee4575060008054600160a060020a0319167351efaf4c8b3c9afbd5ab9f4bbc82784ab6ef8faa179055600161077f565b506000919050565b610ef4612bbe565b60028054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f8a5780601f10610f5f57610100808354040283529160200191610f8a565b820191906000526020600020905b815481529060010190602001808311610f6d57829003601f168201915b5050505050905090565b600080610f9f612bbe565b610fa7612bbe565b610faf612bbe565b610fb7612bbe565b600080610fc2612bbe565b8c604581518110610fcf57fe5b016020015160f860020a9081900481020460660197506020604051805910610ff45750595b818152601f19601f83011681016020016040529050965061101a8d8960208a60006119db565b5060028a8d6000604051602001526040518083805190602001908083835b602083106110575780518252601f199092019160209182019101611038565b6001836020036101000a0380198251168184511617909252505050919091019283525050602090810191506040518083038160008661646e5a03f1151561109d57600080fd5b505060405180519050604051908152602001604051908190039020876040518082805190602001908083835b602083106110e85780518252601f1990920191602091820191016110c9565b6001836020036101000a0380198251168184511617909252505050919091019250604091505051908190039020146111235760009850611512565b8c604a89018151811061113257fe5b016020015160f860020a900460f860020a0260f860020a900460020160405180591061115b5750595b818152601f19601f8301168101602001604052905095506111848d8960490188518960006119db565b5061123c6002876000604051602001526040518082805190602001908083835b602083106111c35780518252601f1990920191602091820191016111a4565b6001836020036101000a03801982511681845116808217855250505050505090500191505060206040518083038160008661646e5a03f1151561120557600080fd5b5050604051805190508c8f60288c018151811061121e57fe5b016020015160f860020a900460f860020a0260f860020a9004611a30565b151561124b5760009850611512565b602960405180591061125a5750595b818152601f19601f8301168101602001604052905094506112838d8960200160298860006119db565b50604080518059106112925750595b818152601f19601f8301168101602001604052905093508551886020016029010160410192506112ca8d6040850360408760006119db565b506002846000604051602001526040518082805190602001908083835b602083106113065780518252601f1990920191602091820191016112e7565b6001836020036101000a03801982511681845116808217855250505050505090500191505060206040518083038160008661646e5a03f1151561134857600080fd5b505060405180519050915084826040518083805190602001908083835b602083106113845780518252601f199092019160209182019101611365565b6001836020036101000a0380198251168184511617909252505050919091019283525050602001905060405190819003902060008d81526003602052604090205414156113df5760008c8152600360205260408120556113e8565b60009850611512565b60496040518059106113f75750595b818152601f19601f83011681016020016040529050905061141d8d8960498460006119db565b506114ae6002826000604051602001526040518082805190602001908083835b6020831061145c5780518252601f19909201916020918201910161143d565b6001836020036101000a03801982511681845116808217855250505050505090500191505060206040518083038160008661646e5a03f1151561149e57600080fd5b5050604051805190508786611ab6565b15156114bd5760009850611512565b60008281526004602052604090205460ff1615156114fd576114df8d84611ccf565b6000838152600460205260409020805460ff19169115159190911790555b60008281526004602052604090205460ff1698505b5050505050505050949350505050565b600061152c612bbe565b611534612bbe565b61153c612bbe565b6000611546612bbe565b61154e612bbe565b611556612bd0565b6000611560612bbe565b8b158061156d575060208c115b1561157757600080fd5b600a8d029c50600160405180591061158c5750595b818152601f19601f8301168101602001604052905098508b60f860020a02896000815181106115b757fe5b906020010190600160f860020a031916908160001a90535060206040518059106115de5750595b818152601f19601f83011681016020016040529050975060206040518059106116045750595b818152601f19601f8301168101602001604052905096506116236120cb565b95506020885242411860014303401860208901526020875285602088015260206040518059106116505750595b818152601f19601f8301168101602001604052905094508c6020860152600860405180591061167c5750595b818152601f19601f8301168101602001604052905093506116a385601860088760006119db565b506080604051908101604052808981526020018a815260200188815260200186815250925061170660408051908101604052600681527f72616e646f6d00000000000000000000000000000000000000000000000000006020820152848d61224c565b915060086040518059106117175750595b8181526020601f909201601f191681018201604052915084015160f860020a810460278301537e01000000000000000000000000000000000000000000000000000000000000810460268301537d0100000000000000000000000000000000000000000000000000000000008104602583015360e060020a810460248301537b01000000000000000000000000000000000000000000000000000000810460238301537a0100000000000000000000000000000000000000000000000000008104602283015379010000000000000000000000000000000000000000000000000081046021830153780100000000000000000000000000000000000000000000000081046020830153506119b882826020860151600287516000604051602001526040518082805190602001908083835b602083106118675780518252601f199092019160209182019101611848565b6001836020036101000a03801982511681845116808217855250505050505090500191505060206040518083038160008661646e5a03f115156118a957600080fd5b50506040518051905060408801516040518085805190602001908083835b602083106118e65780518252601f1990920191602091820191016118c7565b6001836020036101000a038019825116818451161790925250505091909101905084805190602001908083835b602083106119325780518252601f199092019160209182019101611913565b6001836020036101000a038019825116818451161790925250505091909101848152602001905082805190602001908083835b602083106119845780518252601f199092019160209182019101611965565b6001836020036101000a0380198251168184511617909252505050919091019550604094505050505051809103902061245c565b509b9a5050505050505050505050565b60028180516102dc929160200190612bfd565b6119e3612bbe565b83820160008082865110156119f757600080fd5b505060208087019084015b868860200101821015611a2357818901518682015260209182019101611a02565b5093979650505050505050565b600060018183855114611a4257600080fd5b5060005b83811015611aa957848181518110611a5a57fe5b016020015160f860020a900460f860020a02600160f860020a0319168682602081101515611a8457fe5b1a60f860020a02600160f860020a031916141515611aa157600091505b600101611a46565b8192505b50509392505050565b6000806000806000611ac6612bbe565b6000611ad0612bbe565b6020604051805910611adf5750595b8181526020601f909201601f19168101820160405293508a600381518110611b0357fe5b016020015160f860020a900460f860020a0260f860020a9004036004019150611b318a8360208660006119db565b92506020604051805910611b425750595b818152601f19601f830116810160200160405290509050602282019150611b9a8a60208c6001860381518110611b7457fe5b016020015160f860020a900460f860020a0260f860020a900403840160208460006119db565b90506020830151945060208101519350611bb78b601b878761246e565b9097509550600160a060020a038616896040518082805190602001908083835b60208310611bf65780518252601f199092019160209182019101611bd7565b6001836020036101000a0380198251168184511617909252505050919091019250604091505051908190039020600160a060020a03161415611c3b5760019750611cc1565b611c488b601c878761246e565b9097509550600160a060020a038616896040518082805190602001908083835b60208310611c875780518252601f199092019160209182019101611c68565b6001836020036101000a0380198251168184511617909252505050919091019250604091505051908190039020600160a060020a03161497505b505050505050509392505050565b600080611cda612bbe565b611ce2612bbe565b611cea612bbe565b611cf2612bbe565b611cfa612bbe565b611d02612bbe565b611d0a612bbe565b8a8a60010181518110611d1957fe5b016020015160f860020a900460f860020a0260f860020a9004600201604051805910611d425750595b818152601f19601f830116810160200160405290509650611d688b8b89518a60006119db565b5060408051805910611d775750595b818152601f19601f830116810160200160405290509550611d9e8b600460408960006119db565b506062604051805910611dae5750595b818152601f19601f83011681016020016040529050945060f860020a85600081518110611dd757fe5b906020010190600160f860020a031916908160001a905350611e018b60418c0360418860016119db565b506040805190810160405280602081526020017ffd94fa71bc0ba10d39d464d0d8f465efeef0a2764e3887fcc9df41ded20f505c8152509350611e4a84600060208860426119db565b50611edb6002866000604051602001526040518082805190602001908083835b60208310611e895780518252601f199092019160209182019101611e6a565b6001836020036101000a03801982511681845116808217855250505050505090500191505060206040518083038160008661646e5a03f11515611ecb57600080fd5b5050604051805190508888611ab6565b9750871515611eed57600098506120bd565b606060405190810160405280604081526020017f7fb956469c5c9b89840d55b43537e66a98dd4811ea0a27224272c2e5622911e881526020017f537a2f8e86a46baec82864e98dd01e9ccc2f8bc5dfc9cbe5a91a290498dd96e481525092506042604051805910611f5b5750595b818152601f19601f8301168101602001604052905091507ffe0000000000000000000000000000000000000000000000000000000000000082600081518110611fa057fe5b906020010190600160f860020a031916908160001a905350611fc88b600360418560016119db565b508a604581518110611fd657fe5b016020015160f860020a900460f860020a0260f860020a9004600201604051805910611fff5750595b818152601f19601f8301168101602001604052905090506120268b604483518460006119db565b506120b76002836000604051602001526040518082805190602001908083835b602083106120655780518252601f199092019160209182019101612046565b6001836020036101000a03801982511681845116808217855250505050505090500191505060206040518083038160008661646e5a03f115156120a757600080fd5b5050604051805190508285611ab6565b97508798505b505050505050505092915050565b60008054600160a060020a031615806120f657506000546120f490600160a060020a0316610bc2565b155b15612107576121056000610bc6565b505b60008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b151561215057600080fd5b6102c65a03f1151561216157600080fd5b5050506040518051600154600160a060020a0390811691161490506122045760008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b15156121c957600080fd5b6102c65a03f115156121da57600080fd5b505050604051805160018054600160a060020a031916600160a060020a0392909216919091179055505b600154600160a060020a031663abaa5f3e6000604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b15156109ae57600080fd5b6000612256612bbe565b600054600160a060020a03161580612280575060005461227e90600160a060020a0316610bc2565b155b156122915761228f6000610bc6565b505b60008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b15156122da57600080fd5b6102c65a03f115156122eb57600080fd5b5050506040518051600154600160a060020a03908116911614905061238e5760008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b151561235357600080fd5b6102c65a03f1151561236457600080fd5b505050604051805160018054600160a060020a031916600160a060020a0392909216919091179055505b600460405180591061239d5750595b9080825280602002602001820160405280156123d357816020015b6123c0612bbe565b8152602001906001900390816123b85790505b5090508351816000815181106123e557fe5b6020908102909101015283600160200201518160018151811061240457fe5b602090810291909101015260408401518160028151811061242157fe5b602090810291909101015260608401518160038151811061243e57fe5b602090810290910101526124538582856124ae565b95945050505050565b60009182526003602052604090912055565b60008060008060405188815287602082015286604082015285606082015260208160808360006001610bb8f1925080519299929850919650505050505050565b6000806124b9612bbe565b600054600160a060020a031615806124e357506000546124e190600160a060020a0316610bc2565b155b156124f4576124f26000610bc6565b505b60008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b151561253d57600080fd5b6102c65a03f1151561254e57600080fd5b5050506040518051600154600160a060020a0390811691161490506125f15760008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b15156125b657600080fd5b6102c65a03f115156125c757600080fd5b505050604051805160018054600160a060020a031916600160a060020a0392909216919091179055505b600154600160a060020a0316632ef3accc87866000604051602001526040518363ffffffff1660e060020a0281526004018080602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561266257808201518382015260200161264a565b50505050905090810190601f16801561268f5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15156126ae57600080fd5b6102c65a03f115156126bf57600080fd5b5050506040518051925050670de0b6b3a76400003a8502018211156126e75760009250611aad565b6126f085612847565b600154909150600160a060020a031663c55c1cb683600089858983604051602001526040518663ffffffff1660e060020a028152600401808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b83811015612771578082015183820152602001612759565b50505050905090810190601f16801561279e5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b838110156127d45780820151838201526020016127bc565b50505050905090810190601f1680156128015780820380516001836020036101000a031916815260200191505b5096505050505050506020604051808303818588803b151561282257600080fd5b6125ee5a03f1151561283357600080fd5b505050506040518051979650505050505050565b61284f612bbe565b60008061285a612bbe565b6000806000612867612bbe565b60008060008b51995060009850896040518059106128825750595b9080825280602002602001820160405280156128b857816020015b6128a5612bbe565b81526020019060019003908161289d5790505b509750600096505b89871015612945578b87815181106128d457fe5b906020019060200201518888815181106128ea57fe5b602090810290910101526017600189898151811061290457fe5b90602001906020020151510381151561291957fe5b0488888151811061292657fe5b90602001906020020151510198909801600301976001909601956128c0565b600198909801976000955060808a019450886040518059106129645750595b818152601f19601f8301168101602001604052905093505b6001869011156129ce5760f860020a8502866001811061299857fe5b1a60f860020a028487815181106129ab57fe5b906020010190600160f860020a031916908160001a90535060019095019461297c565b600096505b89871015612bae577f5f00000000000000000000000000000000000000000000000000000000000000848781518110612a0857fe5b906020010190600160f860020a031916908160001a905350600190950194600092505b878781518110612a3757fe5b9060200190602002015151831015612b5a57601783061515612af357601883898981518110612a6257fe5b9060200190602002015151031015612a925782888881518110612a8157fe5b906020019060200201515103612a95565b60175b60400191508590505b80860360011115612af35760f860020a820281870360018110612abd57fe5b1a60f860020a02848781518110612ad057fe5b906020010190600160f860020a031916908160001a905350600190950194612a9e565b878781518110612aff57fe5b906020019060200201518381518110612b1457fe5b016020015160f860020a900460f860020a02848781518110612b3257fe5b906020010190600160f860020a031916908160001a9053506001958601959290920191612a2b565b7fff00000000000000000000000000000000000000000000000000000000000000848781518110612b8757fe5b906020010190600160f860020a031916908160001a905350600196870196909501946129d3565b50919a9950505050505050505050565b60206040519081016040526000815290565b60806040519081016040526004815b612be7612bbe565b815260200190600190039081612bdf5790505090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10612c3e57805160ff1916838001178555612c6b565b82800160010185558215612c6b579182015b82811115612c6b578251825591602001919060010190612c50565b50612c77929150612c7b565b5090565b6105fd91905b80821115612c775760008155600101612c81565b600054600160a060020a03161580612cbf5750600054612cbd90600160a060020a0316610bc2565b155b15612cd057612cce6000610bc6565b505b60008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b1515612d1957600080fd5b6102c65a03f11515612d2a57600080fd5b5050506040518051600154600160a060020a039081169116149050612dcd5760008054600160a060020a0316906338cc483190604051602001526040518163ffffffff1660e060020a028152600401602060405180830381600087803b1515612d9257600080fd5b6102c65a03f11515612da357600080fd5b505050604051805160018054600160a060020a031916600160a060020a0392909216919091179055505b600154600160a060020a031663688dcfd78260405160e060020a63ffffffff84160281527fff000000000000000000000000000000000000000000000000000000000000009091166004820152602401600060405180830381600087803b1515612e3657600080fd5b6102c65a03f11515610bbc57600080fd00a165627a7a723058202d73cc6000f65242b8e3f41f301bc3cc92d99c9695e503510eff00cce346ddc40029";

    protected Lottery(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Lottery(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<WinnerPickedEventResponse> getWinnerPickedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WinnerPicked", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<WinnerPickedEventResponse> responses = new ArrayList<WinnerPickedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            WinnerPickedEventResponse typedResponse = new WinnerPickedEventResponse();
            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WinnerPickedEventResponse> winnerPickedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WinnerPicked", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WinnerPickedEventResponse>() {
            @Override
            public WinnerPickedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                WinnerPickedEventResponse typedResponse = new WinnerPickedEventResponse();
                typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OwnershipTransferred", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> __callback(byte[] myid, String result) {
        Function function = new Function(
                "__callback", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(myid), 
                new org.web3j.abi.datatypes.Utf8String(result)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> __callback(byte[] _queryId, String _result, byte[] _proof) {
        Function function = new Function(
                "__callback", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_queryId), 
                new org.web3j.abi.datatypes.Utf8String(_result), 
                new org.web3j.abi.datatypes.DynamicBytes(_proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setJackpot(BigInteger _value) {
        Function function = new Function(
                "setJackpot", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _amount) {
        Function function = new Function(
                "transfer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> lotteryBalance() {
        Function function = new Function("lotteryBalance", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> fundLottery(BigInteger weiValue) {
        Function function = new Function(
                "fundLottery", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> recycle(String _sender) {
        Function function = new Function(
                "recycle", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_sender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> didIWin(String _sender) {
        Function function = new Function("didIWin", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_sender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        Function function = new Function(
                "transferOwnership", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getBalance(String _sender) {
        Function function = new Function("getBalance", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_sender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Lottery> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Lottery.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Lottery> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Lottery.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Lottery load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lottery(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Lottery load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lottery(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class WinnerPickedEventResponse {
        public String winner;
    }

    public static class OwnershipTransferredEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}