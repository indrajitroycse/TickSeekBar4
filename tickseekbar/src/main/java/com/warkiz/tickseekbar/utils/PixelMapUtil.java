/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.warkiz.tickseekbar.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Size;

/**
 * PixelMap Utils to create PixelMap object.
 */
public class PixelMapUtil {
    private static final String TAG = PixelMapUtil.class.getSimpleName();

    private PixelMapUtil() {
    }

    /**
     * PixelMap from resource id.
     *
     * @param context context
     * @param resourceId resource id
     * @param drawableHeight drawable height
     * @return PixelMap object
     */
    public static PixelMap getPixelMapFromResourceId(Context context, int resourceId, int drawableHeight) {
        ResourceManager resourceManager = context.getResourceManager();
        Optional<PixelMap> pixelMap = Optional.empty();

        try {
            Resource resource = resourceManager.getResource(resourceId);
            if (resource == null) {
                return null;
            }
            pixelMap = preparePixelMap(resource, drawableHeight);
            resource.close();
        } catch (IOException | NotExistException e) {
            LogUtil.error(TAG, "set imageview pixelmap failed, pixelmap is empty");
        }
        return pixelMap.get();
    }

    /**
     * Prepare pixelmap object.
     *
     * @param resource resource
     * @param drawableHieght drawable height
     * @return PixelMap object
     * @throws IOException IO Exception
     */
    public static Optional<PixelMap> preparePixelMap(Resource resource, int drawableHieght) throws IOException {
        byte[] bytes;
        PixelMap decodePixelMap = null;
        try {
            if (resource != null) {
                bytes = readBytes(resource);
                resource.close();
                if (bytes != null) {
                    ImageSource.SourceOptions srcOpts = new ImageSource.SourceOptions();
                    ImageSource imageSource = ImageSource.create(bytes, srcOpts);
                    if (imageSource == null) {
                        LogUtil.error(TAG, "get pixelmap failed, image source is null");
                    }
                    ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
                    decodingOpts.desiredSize = new Size(drawableHieght, drawableHieght);
                    decodingOpts.desiredRegion = new ohos.media.image.common.Rect(0, 0, 0, 0);
                    decodingOpts.desiredPixelFormat = PixelFormat.ARGB_8888;
                    if (imageSource != null) {
                        decodePixelMap = imageSource.createPixelmap(decodingOpts);
                    }
                }
            }
        } catch (IOException e) {
            LogUtil.error(TAG, "get pixelmap failed, read resource bytes failed");
        }
        return Optional.ofNullable(decodePixelMap);
    }

    private static byte[] readBytes(Resource resource) {
        final int bufferSize = 1024;
        final int ioEnd = -1;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bytes = new byte[bufferSize];
        byte[] bytesArray = new byte[0];
        while (true) {
            try {
                int readLen = resource.read(bytes, 0, bufferSize);
                if (readLen == ioEnd) {
                    bytesArray = output.toByteArray();
                    break;
                }
                output.write(bytes, 0, readLen);
            } catch (IOException e) {
                LogUtil.error(TAG, e.getMessage());
            } finally {
                try {
                    output.close();
                } catch (IOException e) {
                    LogUtil.error(TAG, e.getMessage());
                }
            }
        }
        return bytesArray;
    }
}