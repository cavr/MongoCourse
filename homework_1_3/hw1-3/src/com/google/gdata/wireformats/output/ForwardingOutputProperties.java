/* Copyright (c) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.gdata.wireformats.output;

import com.google.gdata.wireformats.ForwardingStreamProperties;

/**
 * A {@link OutputProperties} implementation that forwards all
 * calls to another {@link OutputProperties}.
 *
 * <p>Subclass this and override the methods you want modified
 * to create a wrapper for {@link OutputProperties}.
 *
 * 
 */
public class ForwardingOutputProperties extends ForwardingStreamProperties
    implements OutputProperties {
  public ForwardingOutputProperties(OutputProperties delegate) {
    super(delegate);
  }
}
