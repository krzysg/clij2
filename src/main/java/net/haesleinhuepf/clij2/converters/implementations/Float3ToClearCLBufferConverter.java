package net.haesleinhuepf.clij2.converters.implementations;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.converters.AbstractCLIJConverter;
import net.haesleinhuepf.clij.converters.CLIJConverterPlugin;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij2.converters.helptypes.Double3;
import net.haesleinhuepf.clij2.converters.helptypes.Float3;
import org.scijava.plugin.Plugin;

import java.nio.FloatBuffer;

@Plugin(type = CLIJConverterPlugin.class)
public class Float3ToClearCLBufferConverter extends AbstractCLIJConverter<Float3, ClearCLBuffer> {

    @Override
    public ClearCLBuffer convert(Float3 source) {
        long[] dimensions = new long[]{
                source.data[0][0].length,
                source.data[0].length,
                source.data.length
        };

        int numberOfPixelsPerSlice = (int)(dimensions[0] * dimensions[1]);
        long numberOfPixels = numberOfPixelsPerSlice * dimensions[2];


        ClearCLBuffer target = clij.createCLBuffer(dimensions, NativeTypeEnum.Float);
        float[] inputArray = new float[(int)numberOfPixels];

        int count = 0;
        for (int x = 0; x < dimensions[0]; x++) {
            for (int y = 0; y < dimensions[1]; y++) {
                for (int z = 0; z < dimensions[2]; z++) {
                    inputArray[count] = (float)source.data[x][y][z];
                    count++;
                }
            }
        }
        FloatBuffer byteBuffer = FloatBuffer.wrap(inputArray);
        target.readFrom(byteBuffer, true);
        return target;
    }

    @Override
    public Class<Float3> getSourceType() {
        return Float3.class;
    }

    @Override
    public Class<ClearCLBuffer> getTargetType() {
        return ClearCLBuffer.class;
    }
}
