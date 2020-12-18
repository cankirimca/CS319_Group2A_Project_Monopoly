package com.monopoly.model.player;

import com.monopoly.model.player.strategy.AIStrategy;
import com.monopoly.model.player.strategy.GameStatistics;
import com.monopoly.model.tiles.CardTile;
import com.monopoly.model.tiles.IncomeTaxTile;
import com.monopoly.model.tiles.PropertyTile;
import com.monopoly.model.tiles.Tile;
import com.monopoly.model.tiles.property.TitleDeedCard;

public class AIPlayer extends AbstractPlayer {
    AIStrategy aiStrategy;

    /**
     * Default constructor for AIPlayer
     * @param aiStrategy used for decisionmaking in certain situations
     */
    public AIPlayer(AIStrategy aiStrategy) {
        this.aiStrategy = aiStrategy;
    }

    /**
     * Makes a decision and executes it according to its strategy. Is invoked when player lands on a PropertyTile.
     */
    void makeAndExecutePropertyDecision(){
        aiStrategy.makeAndExecutePropertyDecision( this);
    }

    /**
     * Makes a decision and executes it according to its strategy. Is invoked when player lands on a IncomeTaxTile.
     */
    void makeAndExecuteIncomeTaxDecision(){
        aiStrategy.makeAndExecuteIncomeTaxDecision(this);
    }

    /**
     * Makes a decision and executes it according to its strategy. Is invoked when player lands on a CardTile.
     */
    void makeAndExecuteCardDecision(){
        aiStrategy.makeAndExecuteCardDecision( this);
    }

    /**
     * Makes a decision  to accept or reject a trade offer and executes that decision according to its strategy.
     * Is invoked when player is made a trade offer.
     */
    void makeAndExecuteTradeDecision(){
        aiStrategy.makeAndExecuteTradeDecision( this);
    }

    /**
     * Makes and executes a decision  to bid or not bid to an auction at its given state according to its strategy.
     * Is invoked when player is involved in an auction.
     */
    void makeAndExecuteAuctionDecision(){
        aiStrategy.makeAndExecuteAuctionDecision( this);
    }



    /**
     * Plays the turn for an AI Player.
     */
    @Override
    public void playTurn(){
        // if player is in jail, tries to bail out of jail
        if( isInJail()){
            if( getBailOutOfJailCards().size() > 0){
                setGetOutOfJailChoice(BailOutChoice.BAIL_OUT_CARD);
            }
            else if( liquidTotalWorth < getPlayerToken().getBoard().getBoardSalary() / 4){
                setGetOutOfJailChoice(BailOutChoice.DOUBLE_DICE);
            }
            else{
                if( getBalance() >= (getPlayerToken().getBoard().getBoardSalary() / 4) ){
                    setGetOutOfJailChoice(BailOutChoice.MONEY);
                }
                // TODO add upgradedable && downgradeable (isDowngradeable()) to TitleDeedCard
                else{
                    for(TitleDeedCard titleDeedCard: getTitleDeeds()){
                        if( titleDeedCard.getUpgradeLevel() > 1 && titleDeedCard.isDowngradeable() && getBalance() < (getPlayerToken().getBoard().getBoardSalary() / 4)){
                            titleDeedCard.downgrade();
                        }
                    }
                    if( getBalance() < (getPlayerToken().getBoard().getBoardSalary() / 4)){
                        for(TitleDeedCard titleDeedCard: getTitleDeeds()){
                            if( titleDeedCard.isMortgaged() == false && getBalance() < (getPlayerToken().getBoard().getBoardSalary() /4)){
                                titleDeedCard.mortgage();
                            }
                        }
                    }
                    if( getBalance() < (getPlayerToken().getBoard().getBoardSalary() / 4)){
                        System.out.println( "ERROR: Calculations incorrect!");
                        setGetOutOfJailChoice(BailOutChoice.DOUBLE_DICE);
                    }
                    else{
                        setGetOutOfJailChoice(BailOutChoice.MONEY);
                    }
                }
            }
        }
        // if player is not in jail, plays turn normally
        else{
            rollDice();
            if( getConsecutiveDoubleCount() == 3){
                goToJail();
            }
            // if the AI player did not throw 3 double dice
            else {
                moveToken( playersDice.getDiceResultSum());
                Tile currentlyLandedTile = getCurrentTile();
                if( currentlyLandedTile instanceof PropertyTile){
                    makeAndExecutePropertyDecision();
                }
                else if( currentlyLandedTile instanceof CardTile){
                    //TODO
                }
                else if( currentlyLandedTile instanceof IncomeTaxTile){
                    makeAndExecuteIncomeTaxDecision();
                }
                // if not on a tile that requires a specific action, do not do anything
            }
        }
    }



}
