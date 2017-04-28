package inc.a13xis.legacy.dendrology.block;

import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.dendrology.item.ModDoorItem;
import inc.a13xis.legacy.koresample.common.block.DoorBlock;
import inc.a13xis.legacy.koresample.tree.DefinesDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModDoorBlock extends DoorBlock
{
    public ModDoorBlock(DefinesDoor definition)
    {
        super(definition);
        setCreativeTab(TheMod.INSTANCE.creativeTab());
    }

    @Override
    protected String resourcePrefix()
    {
        return TheMod.getResourcePrefix();
    }

    @Override
    protected Item getItem() {
        return Item.getItemFromBlock(this);
    }

    public void registerBlockModel()
    {
        ModelResourceLocation typeLocation = new ModelResourceLocation(new ResourceLocation("dendrology","door"+"_"+variant.name().toLowerCase()),"inventory");
        Item blockItem = Item.getItemFromBlock(ModBlocks.getDoorBlock(variant.ordinal()));
        ModelLoader.setCustomModelResourceLocation(blockItem,0,typeLocation);
    }

    /*
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState();
        if((meta/2)==0){
            state = state.withProperty(HALF,EnumHalf.BOTTOM);
        }
        else{
            state = state.withProperty(HALF,EnumHalf.TOP);
        }
        switch(meta%4){
            case 0: state = state.withProperty(FACING,EnumFacing.EAST);
                break;
            case 1: state = state.withProperty(FACING,EnumFacing.WEST);
                break;
            case 2: state = state.withProperty(FACING,EnumFacing.SOUTH);
                break;
            case 3: state = state.withProperty(FACING,EnumFacing.NORTH);
                break;
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
       int par = state.getValue(HALF).equals(EnumHalf.BOTTOM)?0:4;
       int meta;
       switch((EnumFacing)state.getValue(FACING)){
           case NORTH:
               meta = 3;
               break;
           case SOUTH:
               meta = 2;
               break;
           case WEST:
               meta = 1;
               break;
           case EAST:
               meta = 0;
               break;
           default:
              return -1;
       }
       return par+meta;
    }*/


    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
