package net.haesleinhuepf.clijx.plugins;


import ij.gui.Roi;
import ij.plugin.frame.RoiManager;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.utilities.AbstractCLIJxPlugin;
import org.scijava.plugin.Plugin;

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_pullLabelsToROIManager")
public class PullLabelsToROIManager extends AbstractCLIJxPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public String getParameterHelpText() {
        return "Image binary_input";
    }

    @Override
    public boolean executeCL() {
        CLIJx clijx = getCLIJx();
        return pullLabelsToROIManager(clijx, (ClearCLBuffer) args[0]);
    }




    public static boolean pullLabelsToROIManager(CLIJx clijx, ClearCLBuffer labelMap ) {
        RoiManager rm = RoiManager.getInstance();
        if (rm == null) {
            rm = new RoiManager();
        }

        return pullLabelsToROIManager(clijx, labelMap, rm);
    }

    public static boolean pullLabelsToROIManager(CLIJx clijx, ClearCLBuffer labelMap, RoiManager roiManager ) {

        ClearCLBuffer binary = clijx.create(labelMap);
        int numberOfLabels = (int) clijx.maximumOfAllPixels(labelMap);
        for (int i = 1; i < numberOfLabels; i++) {
            EqualConstant.equalConstant(clijx, labelMap, binary, new Float(i));
            Roi roi = PullAsROI.pullAsROI(clijx, binary);
            roiManager.addRoi(roi);
        }
        clijx.release(binary);
        return true;
    }

    @Override
    public String getDescription() {
        return "Pulls all labels in a label map as ROIs to the ROI manager.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D";
    }
}