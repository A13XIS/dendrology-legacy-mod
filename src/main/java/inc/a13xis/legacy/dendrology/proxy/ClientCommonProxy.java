package inc.a13xis.legacy.dendrology.proxy;

import inc.a13xis.legacy.dendrology.block.ModBlocks;
import inc.a13xis.legacy.dendrology.item.ModItems;

/**
 * Created by lexis on 21.01.17.
 */
public class ClientCommonProxy extends CommonProxy {

    @Override
    public void registerRenders(){
        ModItems.registerAllItemRenders();
        ModBlocks.registerAllRenders();
    }


}
