package foxiwhitee.FoxAE2Upgrade.asm.ae2.cables;

import foxiwhitee.FoxAE2Upgrade.utils.asm.ASMClassTransformer;
import foxiwhitee.FoxAE2Upgrade.utils.asm.SpecialClassNode;
import org.objectweb.asm.ClassReader;

public class ContainerUpgradeableTransformer implements ASMClassTransformer {
  public ASMClassTransformer.TransformResult transformClass(String name, String transformedName, ClassReader reader, SpecialClassNode classNode) {
    classNode.addInterface("foxiwhitee/FoxAE2Upgrade/utils/cables/IContainerUpgradeableAccessor").invoke("call").build();
    return ASMClassTransformer.TransformResult.MODIFIED_STACK;
  }
}
