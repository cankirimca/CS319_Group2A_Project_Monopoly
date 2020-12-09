package com.monopoly.model.player;

import com.monopoly.model.tiles.Tile;
import com.monopoly.model.tiles.card.Card;
import com.monopoly.model.tiles.property.TitleDeedCard;

import java.util.ArrayList;

public abstract class AbstractPlayer implements Player {
    private int taxOption;
    private ArrayList<TitleDeedCard> titleDeeds;
    private ArrayList<Card> cards;
    private int balance;
    private boolean bankrupt;
    private int consecutiveDoubleCount;

    // newly added
    private int inJailFor;
    private int currTileInd;
    private PlayerToken playerToken;
    private boolean isInJail;

    AbstractPlayer( ) {

    }
    public void setTaxOption(int taxOption) {
        this.taxOption = taxOption;
    }

    public ArrayList<TitleDeedCard> getTitleDeeds() {
        return titleDeeds;
    }

    public void setTitleDeeds(ArrayList<TitleDeedCard> titleDeeds) {
        this.titleDeeds = titleDeeds;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    public void setConsecutiveDoubleCount(int consecutiveDoubleCount) {
        this.consecutiveDoubleCount = consecutiveDoubleCount;
    }


    /*
    *   If player has waited in jail for less than 3 turns,
    *   waits the player in the jail for 1 turn, without doing anything. Returns true in this case.
    *   Otherwise returns false.
    */
    @Override
    public boolean waitInJail() {
        if( inJailFor < 3){
            inJailFor++;
            return true;
        }
        else{
            return false;
        }
    }

    /*
    * Problematic
    */
    @Override
    public void checkBailOut() {

    }


    /*
    *  Adds the bailOutOfJail card to player's inventory.
    *  @param card is the card to add to the inventory.
    */
    @Override
    public void addBailOutFromJailCard(Card card) {
        cards.add( card);
    }

    /*
    * Removes the last from players bailOutOfJail cards.
    */
    @Override
    public void removeBailOutFromJailCard() {
        cards.remove( cards.size()-1);
    }


    /*
    * Calls the PlayerToken's move method to move the player on the board by specified amount.
    * Updates currTileInd accordingly.
    * @param amount is the number of tiles to go.
     */
    @Override
    public void moveToken(int amount) {
        currTileInd = playerToken.move(amount).getIndex();
    }

    /*
    * Sends player to jail by calling the PlayerToken's gotoJail method.
     */
    @Override
    public void goToJail() {
        isInJail = true;
        playerToken.goToJail();
    }

    /*
    * Adds the TitleDeedCard to player's titleDeeds.
    * @param card is the newly acquired TitleDeedCard.
     */
    @Override
    public void addTitleDeedCard(TitleDeedCard card) {
        titleDeeds.add( card);
    }

    /*
    * Removes the given TitleDeedCard from player's titleDeeds.
    * @param card is the card being removed.
     */
    @Override
    public void removeTitleDeedCard(TitleDeedCard card) {
        titleDeeds.remove( card);
    }

    @Override
    public void changeBalance(int amount) {

    }

    @Override
    public void declareBankruptcy() {

    }

    @Override
    public int getCurrentTileIndex() {
        return 0;
    }

    @Override
    public int getTaxOption() {
        return 0;
    }

    @Override
    public int setTaxOption() {
        return 0;
    }

    @Override
    public int getBalance() {
        return 0;
    }

    @Override
    public int getConsecutiveDoubleCount() {
        return 0;
    }

    @Override
    public String getTokenType() {
        return null;
    }
}
