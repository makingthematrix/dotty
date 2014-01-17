/* NSC -- new Scala compiler
 * Copyright 2005-2013 LAMP/EPFL
 * @author  Martin Odersky
 */
package dotty.tools
package dotc

import core.Contexts.Context
import reporting.Reporter

/* To do:
 *   - simplify hk types
 *   - make use of AndOrType
 *   - review isSubType
 *   - have a second look at normalization (leave at method types if pt is method type?)
 *   - fix problem with duplicate companion objects for classes with default parameters in constructors
 *   - Check usages of isAliasType and replace where possible by looking at the info.
 *   - Don't open package objects from class files if they are present in source
 */

object Main extends Driver {
  def resident(compiler: Compiler): Reporter = unsupported("resident") /*loop { line =>
    val command = new CompilerCommand(line split "\\s+" toList, new Settings(scalacError))
    compiler.reporter.reset()
    new compiler.Run() compile command.files
  }*/

  override def newCompiler(): Compiler = new Compiler

  override def doCompile(compiler: Compiler, fileNames: List[String])(implicit ctx: Context): Reporter = {
    if (new config.Settings.Setting.SettingDecorator[Boolean](ctx.base.settings.resident).value(ctx))
      resident(compiler)
    else
      super.doCompile(compiler, fileNames)
  }
}
