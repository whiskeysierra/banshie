package org.whiskeysierra.banshie.execution.process;

import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface RunningProcess extends InputSupplier<InputStream>, OutputSupplier<OutputStream> {

    void await() throws IOException;

    void cancel();

}
