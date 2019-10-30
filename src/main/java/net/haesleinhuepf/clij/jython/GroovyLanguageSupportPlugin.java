package net.haesleinhuepf.clij.jython;

import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.script.LanguageSupportPlugin;

@Plugin(type = LanguageSupportPlugin.class)
public class GroovyLanguageSupportPlugin extends CLIJAutoCompleteSupportPlugin {
    @Override
    public String getLanguageName() {
        return "Groovy";
    }
}