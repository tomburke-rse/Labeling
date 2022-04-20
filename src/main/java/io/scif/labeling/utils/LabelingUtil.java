/*-
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2020 - 2022 SCIFIO developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.scif.labeling.utils;

import io.scif.config.SCIFIOConfig;
import io.scif.img.ImgIOException;
import io.scif.img.ImgSaver;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.ImgView;
import net.imglib2.type.numeric.RealType;
import org.scijava.Context;

import java.nio.file.Paths;

public class LabelingUtil {


    public static final String LBL_ENDING = ".lbl.json";
    public static final String TIF_ENDING = ".tif";
    public final static int VERSION = 3;

    /**
     * @param context  the scijava context used in the project
     * @param filename the filename of the Img to save
     * @param rai      the img
     * @param <T>      the pixel value
     */
    public static <T extends RealType<T>> void saveAsTiff(final Context context,
                                                          final String filename,
                                                          final RandomAccessibleInterval<T> rai) {

        try {
            new ImgSaver(context).saveImg(filename, ImgView.wrap(rai, null), new SCIFIOConfig().writerSetFailIfOverwriting(false));
        } catch (ImgIOException | IncompatibleTypeException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePathWithExtension(final String filename, final String extension, String path) {
        path = (path == null) ? "" : path;
        String actualFilename = Paths.get(filename).getFileName().toString();
        if (actualFilename.endsWith(extension)) {
            return Paths.get(path, actualFilename).toString();
        }
        final int index = actualFilename.lastIndexOf(".");
        return Paths.get(path, actualFilename.substring(0, index == -1 ? actualFilename.length() : index).concat(extension)).toString();
    }
}
