/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.layout.json.template.resolver;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.layout.json.template.util.JsonWriter;

final class MarkerResolver implements EventResolver {

    private static final TemplateResolver<LogEvent> NAME_RESOLVER =
            (final LogEvent logEvent, final JsonWriter jsonWriter) -> {
                final Marker marker = logEvent.getMarker();
                if (marker == null) {
                    jsonWriter.writeNull();
                } else {
                    jsonWriter.writeString(marker.getName());
                }
            };

    private final TemplateResolver<LogEvent> internalResolver;

    MarkerResolver(final String key) {
        this.internalResolver = createInternalResolver(key);
    }

    private TemplateResolver<LogEvent> createInternalResolver(final String key) {
        if ("name".equals(key)) {
            return NAME_RESOLVER;
        }
        throw new IllegalArgumentException("unknown key: " + key);
    }

    static String getName() {
        return "marker";
    }

    @Override
    public boolean isResolvable(final LogEvent logEvent) {
        return logEvent.getMarker() != null;
    }

    @Override
    public void resolve(
            final LogEvent logEvent,
            final JsonWriter jsonWriter) {
        internalResolver.resolve(logEvent, jsonWriter);
    }

}
