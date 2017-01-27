package inc.a13xis.legacy.dendrology.block;

import com.google.common.collect.ImmutableList;
import inc.a13xis.legacy.dendrology.TheMod;
import inc.a13xis.legacy.koresample.tree.DefinesWood;
import inc.a13xis.legacy.koresample.tree.block.WoodBlock;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import java.util.Collection;


public final class ModWoodBlock extends WoodBlock
{
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", ModWoodBlock.EnumType.class);

    protected ModWoodBlock(Collection<? extends DefinesWood> subBlocks){
        super(subBlocks);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ModWoodBlock.VARIANT, ModWoodBlock.EnumType.ACEMUS));
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { ModWoodBlock.VARIANT });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return meta<8?getDefaultState().withProperty(ModWoodBlock.VARIANT, EnumType.fromId(0,meta)):getDefaultState().withProperty(ModWoodBlock.VARIANT, EnumType.fromId(1,meta-8));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumType type = (EnumType) state.getValue(ModWoodBlock.VARIANT);
        return type.getId();
    }

    public ModWoodBlock(Iterable<? extends DefinesWood> subBlocks)
    {
        super(ImmutableList.copyOf(subBlocks));
        setCreativeTab(TheMod.INSTANCE.creativeTab());
        setHardness(2.0f);
        setResistance(5.0f);
        setStepSound(soundTypeWood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ModWoodBlock.VARIANT, EnumType.ACEMUS));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }


    public IBlockState getBlockState(int id){
       return ModWoodBlock.getStateById(id);
    }

    @Override
    protected String resourcePrefix() { return TheMod.getResourcePrefix(); }

    public enum EnumType implements IStringSerializable{
        ACEMUS(0,"acemus"),
        CEDRUM(1,"cedrum"),
        CERASU(2,"cerasu"),
        DELNAS(3,"delnas"),
        EWCALY(4,"ewcaly"),
        HEKUR(5,"hekur"),
        KIPARIS(6,"kiparis"),
        KULIST(7,"kulist"),
        LATA(0,"lata"),
        NUCIS(1,"nucis"),
        PORFFOR(2,"porffor"),
        SALYX(3,"salyx"),
        TUOPA(4,"tuopa");

        private final String species;
        private final int ID;
        private int id;

        EnumType(int id,String name){
            this.species=name;
            this.ID=id;
        }

        public String getName(){
            return species;
        }

        @Override
        public String toString() {
            return getName();
        }

        public static EnumType fromId(int bidbit,int id) {
            if(bidbit>1||id<0||(bidbit==0&&id>7)||(bidbit==1&&id>4)){
                return ACEMUS;
            }
            else{
                return EnumType.values()[(bidbit*8)+id];
            }
        }

        public int getId() {
            return id;
        }
    }

}
