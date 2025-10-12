package foxiwhitee.FoxAE2Upgrade.asm.ae2.cables;

import foxiwhitee.FoxAE2Upgrade.asm.ae2.AETransformer;
import foxiwhitee.FoxAE2Upgrade.utils.asm.ASMClassTransformer;
import foxiwhitee.FoxAE2Upgrade.utils.asm.SpecialClassNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;


public class AENetworkProxyTransformer implements ASMClassTransformer {
  public ASMClassTransformer.TransformResult transformClass(String name, String transformedName, ClassReader reader, SpecialClassNode classNode) {
    MethodNode node = classNode.getMethod("onGridNotification");
    InsnList list = new InsnList();
    list.add((AbstractInsnNode)new VarInsnNode(25, 0));
    list.add((AbstractInsnNode)new MethodInsnNode(184, AETransformer.HOOKS, "onGridNotification", "(Lappeng/me/helpers/AENetworkProxy;)V", false));
    node.instructions.insert(list);
    return ASMClassTransformer.TransformResult.MODIFIED;
  }
}
