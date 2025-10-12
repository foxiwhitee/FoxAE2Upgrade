package foxiwhitee.FoxAE2Upgrade.asm.ae2.cables;


import foxiwhitee.FoxAE2Upgrade.asm.ae2.AETransformer;
import foxiwhitee.FoxAE2Upgrade.utils.asm.ASMClassTransformer;
import foxiwhitee.FoxAE2Upgrade.utils.asm.ASMUtils;
import foxiwhitee.FoxAE2Upgrade.utils.asm.SpecialClassNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.List;

public class GridNodeTransformer implements ASMClassTransformer {
  public ASMClassTransformer.TransformResult transformClass(String name, String transformedName, ClassReader reader, SpecialClassNode classNode) {
    MethodNode node = classNode.getMethod("getMaxChannels");
    List<AbstractInsnNode> points = ASMUtils.findAllOpcodes(node.instructions, 172);
    if (points.isEmpty())
      return ASMClassTransformer.TransformResult.NOT_MODIFIED;
    InsnList list = new InsnList();
    list.add((AbstractInsnNode)new VarInsnNode(25, 0));
    list.add((AbstractInsnNode)new InsnNode(95));
    list.add((AbstractInsnNode)new MethodInsnNode(184, AETransformer.HOOKS, "getMaxChannelCount", "(Lappeng/me/GridNode;I)I", false));
    points.forEach(e -> node.instructions.insertBefore(e, ASMUtils.copy(list)));
    return ASMClassTransformer.TransformResult.MODIFIED_STACK;
  }
}

