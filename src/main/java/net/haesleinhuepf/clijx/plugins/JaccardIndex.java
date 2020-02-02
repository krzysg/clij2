package net.haesleinhuepf.clijx.plugins;

import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.utilities.AbstractCLIJxPlugin;
import org.scijava.plugin.Plugin;

/**
 * Author: @haesleinhuepf
 * July 2019
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_jaccardIndex")
public class JaccardIndex extends AbstractCLIJxPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {

        ClearCLBuffer buffer1 = (ClearCLBuffer)( args[0]);
        ClearCLBuffer buffer2 = (ClearCLBuffer)( args[1]);

        double jaccardIndex = jaccardIndex(getCLIJx(), buffer1, buffer2);

        ResultsTable table = ResultsTable.getResultsTable();
        table.incrementCounter();
        table.addValue("Jaccard_Index", jaccardIndex);
        table.show("Results");
        return true;
    }

    public static double jaccardIndex(CLIJx clijx, ClearCLBuffer input1, ClearCLBuffer input2) {
        ClearCLBuffer intersection = clijx.create(input1.getDimensions(), NativeTypeEnum.Byte);
        ClearCLBuffer union = clijx.create(input1.getDimensions(), NativeTypeEnum.Byte);

        BinaryIntersection.binaryIntersection(clijx, input1, input2, intersection);
        BinaryUnion.binaryUnion(clijx, input1, input2, union);

        double countIntersection = CountNonZeroPixels.countNonZeroPixels(clijx, intersection);
        double countUnion = CountNonZeroPixels.countNonZeroPixels(clijx, union);

        clijx.release(intersection);
        clijx.release(union);

        double jaccardIndex = countIntersection / countUnion;
        return jaccardIndex;
    }

    @Override
    public String getParameterHelpText() {
        return "Image source1, Image source2";
    }

    @Override
    public String getDescription() {
        return "Determines the overlap of two binary images using the Jaccard index.\n" +
                "A value of 0 suggests no overlap, 1 means perfect overlap.\n" +
                "The resulting Jaccard index is saved to the results table in the 'Jaccard_Index' column.\n" +
                "Note that the Sorensen-Dice coefficient can be calculated from the Jaccard index j using this formula:\n" +
                "<pre>s = f(j) = 2 j / (j + 1)</pre>";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

}