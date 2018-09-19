/*
Copyright 2017-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License").
You may not use this file except in compliance with the License.
A copy of the License is located at

   http://aws.amazon.com/apache2.0/

or in the "license" file accompanying this file.
This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
*/
package com.amazonaws.kinesisvideo.parser.utilities;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

import com.amazonaws.kinesisvideo.parser.examples.KinesisVideoFrameViewer;
import com.amazonaws.kinesisvideo.parser.mkv.Frame;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.codecs.h264.H264Decoder;
import org.jcodec.codecs.h264.mp4.AvcCBox;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.jcodec.scale.Transform;
import org.jcodec.scale.Yuv420jToRgb;

import static org.jcodec.codecs.h264.H264Utils.splitMOVPacket;

@Slf4j
public class H264FrameRenderer extends H264FrameDecoder {

    private final KinesisVideoFrameViewer kinesisVideoFrameViewer;

    protected H264FrameRenderer(final KinesisVideoFrameViewer kinesisVideoFrameViewer) {
        super();
        this.kinesisVideoFrameViewer = kinesisVideoFrameViewer;
        this.kinesisVideoFrameViewer.setVisible(true);
    }

    public static H264FrameRenderer create(KinesisVideoFrameViewer kinesisVideoFrameViewer) {
        return new H264FrameRenderer(kinesisVideoFrameViewer);
    }

    @Override
    public void process(Frame frame, MkvTrackMetadata trackMetadata, Optional<FragmentMetadata> fragmentMetadata) {
        final BufferedImage bufferedImage = decodeH264Frame(frame, trackMetadata);
        kinesisVideoFrameViewer.update(bufferedImage);
    }
}
