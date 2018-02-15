const Lottery = artifacts.require('Lottery')

contract('Lottery', function(accounts) {
  it('sets the first account as the contract creator', async function() {
    const contract = await Lottery.deployed();

    const expected = accounts[0];
    const owner = await contract.owner();

    assert.equal(owner, expected, 'main account is the creator')
  });

  it('initially has zero funds', async function() {
    const contract = await Lottery.deployed();
    const owner = await contract.owner();

    // call the function from the owner because access is restricted
    let funds = await contract.lotteryBalance({ from: owner });
    assert.equal(funds.toString(), 0, 'contract initially has zero funds');
  });

  it('can be funded', async function () {
    const contract = await Lottery.deployed();
    const owner = await contract.owner();

    const amount = 1;

    // call the function from the owner because access is restricted
    await contract.fundLottery({ from: owner, value: amount });

    // call the function from the owner because access is restricted
    let funds = await contract.lotteryBalance({ from: owner });
    assert.equal(funds.toString(), 1, 'contract was funded with ' + amount + ' ether');
  });
});
