package net.haesleinhuepf.clij2.plugins;

import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

/**
 * Author: @haesleinhuepf
 *         April 2020
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_getSorensenDiceCoefficient")
public class GetSorensenDiceCoefficient extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized {

    @Override
    public boolean executeCL() {

        ClearCLBuffer buffer1 = (ClearCLBuffer)( args[0]);
        ClearCLBuffer buffer2 = (ClearCLBuffer)( args[1]);

        double sorensenDiceCoefficient = getCLIJ2().getSorensenDiceCoefficient(buffer1, buffer2);


        ((Double[])args[2])[0] = sorensenDiceCoefficient;
        return true;
    }

    public static double getSorensenDiceCoefficient(CLIJ2 clij2, ClearCLBuffer input1, ClearCLBuffer input2) {
        return clij2.sorensenDiceCoefficient(input1, input2);
    }


    @Override
    public String getParameterHelpText() {
        return "Image source1, Image source2, ByRef Number sorensen_dice_coefficient";
    }

    @Override
    public String getDescription() {
        return new SorensenDiceCoefficient().getDescription();
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Binary, Measurement";
    }
}
