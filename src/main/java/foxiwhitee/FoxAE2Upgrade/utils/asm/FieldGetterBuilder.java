package foxiwhitee.FoxAE2Upgrade.utils.asm;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Locale;

public class FieldGetterBuilder {
    private final SpecialClassNode classNode;

    private final String name;

    private String desc = "";

    private boolean isStatic = false;

    private String getterMethodName = "";

    public FieldGetterBuilder(SpecialClassNode classNode, String name) {
        this.classNode = classNode;
        this.name = name;
    }

    public FieldGetterBuilder desc(String desc) {
        this.desc = desc;
        return this;
    }

    public FieldGetterBuilder setStatic(boolean aStatic) {
        this.isStatic = aStatic;
        return this;
    }

    public FieldGetterBuilder getterMethodName(String name) {
        this.getterMethodName = name;
        return this;
    }

    public void build() {
        FieldNode fieldNode = this.desc.isEmpty() ? this.classNode.getField(this.name) : this.classNode.getField(this.name, this.desc);
        if (this.getterMethodName.isEmpty())
            this.getterMethodName = "get" + this.name.substring(0, 1).toUpperCase(Locale.ROOT) + this.name.substring(1);
        MethodNode node = new MethodNode(0x1 | (this.isStatic ? 8 : 0), this.getterMethodName, "()" + fieldNode.desc, null, null);
        InsnList list = node.instructions;
        if (!this.isStatic)
            list.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list.add((AbstractInsnNode)new FieldInsnNode(this.isStatic ? 178 : 180, this.classNode.name, fieldNode.name, fieldNode.desc));
        list.add((AbstractInsnNode)new InsnNode(ASMUtils.getReturnOpcodeFromType(Type.getType(fieldNode.desc))));
        this.classNode.methods.add(node);
    }
}
