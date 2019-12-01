## averageDistanceOfClosestPoints
![Image](images/mini_clijx_logo.png)

Determine the n point indices with shortest distance for all points in a distance matrix.
This corresponds to the n row indices with minimum values for each column of the distance matrix.

### Usage in ImageJ macro
```
Ext.CLIJx_averageDistanceOfClosestPoints(Image distance_matrix, Image indexlist_destination, Number nClosestPointsTofind);
```


### Usage in Java
```
// init CLIJ and GPU
import net.haesleinhuepf.clijx.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
CLIJx clijx = CLIJx.getInstance();

// get input parameters
ClearCLBuffer arg1 = clijx.push(arg1ImagePlus);
ClearCLBuffer arg2 = clijx.push(arg2ImagePlus);
int arg3 = 10;
```

```
// Execute operation on GPU
clijx.averageDistanceOfClosestPoints(clij, arg1, arg2, arg3);
```

```
//show result

// cleanup memory on GPU
arg1.close();
arg2.close();
```


[Back to CLIJ documentation](https://clij.github.io/)

[Imprint](https://clij.github.io/imprint)