package model.tiles.actionStrategy;

import model.player.Player;
import model.tiles.property.TitleDeedCard;

import java.util.ArrayList;

public class PropertyActionsStrategy extends ActionStrategy {

    /**
     * This strategy is related to the upgrade property action.
     * @param player is the player that the action is inflicted on.
      */
    @Override
    public void button1Strategy(Player player) {
        upgradeProperty ( player );
    }

    /**
     * This strategy is related to the downgrade property action.
     * @param player is the player that the action is inflicted on.
     */
    @Override
    public void button2Strategy(Player player) {
        downgradeProperty ( player );
    }

    /**
     * This strategy is related to the mortgage a property action.
     * @param player is the player that the action is inflicted on.
     */
    @Override
    public void button3Strategy(Player player) {
        // TODO NULLPOINTEREXCEPTION veriyor
        if( player == null){
            System.out.println( "PLAYER IS NULL");
        }
        mortgageProperty(player);
    }

    /**
     * This strategy is related to removing the mortgage of a property.
     * @param player is the player that the action is inflicted on.
     */
    @Override
    public void button4Strategy(Player player) {
        removeMortgage(player);
    }

    /**
     * This method is the helper method for the button1Strategy.
     * @param player the player that the action is inflicted on.
     */
    private void upgradeProperty(Player player) {

        TitleDeedCard card = player.getSelectedTitleDeed();

        if (player.getBalance() <= card.getUpgradeCost() && card.isUpgradeable()) {
            player.changeBalance(-card.upgrade());
        }
    }

    /**
     * This method is the helper method for the button2Strategy.
     * @param player the player that the action is inflicted on.
     */
    private void downgradeProperty(Player player) {
        TitleDeedCard card = player.getSelectedTitleDeed();

        if (card.isDowngradeable()) {
            player.changeBalance(card.downgrade());
        }
    }

    /**
     * This method is the helper method for the button3Strategy.
     * @param player the player that the action is inflicted on.
     */
    // TODO NULLPTRXCEPTION
    private void mortgageProperty(Player player){
        if( player == null){
            System.out.println( "PLAYER IS NULL at second call");
        }
        //ArrayList<TitleDeedCard> card = player.getTitleDeeds();
        // todo big problems
        TitleDeedCard card = player.getSelectedTitleDeed();
        // TODO DEBUG REMOVE LATER
        if( card == null){
            System.out.println("CARD IS NULL");
        }
        if(!card.isMortgaged()){
            card.resetProperty();
            int mortgageMoney = card.mortgage();
            player.changeBalance(mortgageMoney);
        }
    }

    /**
     * This method is the helper method for the button4Strategy.
     * @param player the player that the action is inflicted on.
     */
    private void removeMortgage(Player player){
        TitleDeedCard card = player.getSelectedTitleDeed();

        if(card.isMortgaged()){
            int mortgageRemovalFee = card.removeMortgage();
            player.changeBalance(-mortgageRemovalFee);
        }
    }
}
