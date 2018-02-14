pragma solidity ^0.4.19;

contract Token {
    mapping (address => uint256) public balanceOf;
    address public owner = msg.sender;

    function Token() public {
        //balanceOf[msg.sender] = 1000;
    }

    function buyTockens() payable public{
        owner.transfer(msg.value);
        balanceOf[msg.sender] += msg.value;
    }

    function transfer(address to, uint256 amount) public{
        require(amount <= balanceOf[msg.sender]);
        balanceOf[to] += amount;
        balanceOf[msg.sender] -= amount;
    }
}