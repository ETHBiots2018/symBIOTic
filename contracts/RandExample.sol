/*
   Example for getting a random number from rand.org
*/


pragma solidity ^0.4.0;
import "github.com/oraclize/ethereum-api/oraclizeAPI.sol";

contract RandExample is usingOraclize {
    
    string public number;
    
    event NewOraclizeQuery(string description);
    event NewRandNumber(uint256 rand);

    function RandExample() {
        update();
    }
    
    function __callback(bytes32 _myid, string _result) {
        if (msg.sender != oraclize_cbAddress()) 
            throw;
        number = _result;
        uint256 rand = stringToUint(number);
        NewRandNumber(rand);
        // do something with the temperature measure..
    }
    
    /*function checkTemp(uint256 _temp) public returns (uint256) {
        return _temp;
    }*/
    
    function update() payable {
        newOraclizeQuery("Oraclize query was sent, standing by for the answer..");
        oraclize_query("URL", "https://www.random.org/integers/?num=1&min=1&max=6&col=1&base=10&format=plain&rnd=new");
    }
    
    //copying and pasting from StackOverflow - to the moon and back
    function stringToUint(string s) constant returns (uint) {
        bytes memory b = bytes(s);
        uint result = 0;
        for (uint i = 0; i < b.length; i++) { 
            if (b[i] >= 48 && b[i] <= 57) {
                result = result * 10 + (uint(b[i]) - 48);
            }
        }
        return result;
    }
} 
                                           
