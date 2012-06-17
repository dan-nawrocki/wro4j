/**
 * Copyright Alex Objelean
 */
package ro.isdc.wro.model.resource.support.naming;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import ro.isdc.wro.model.resource.support.hash.CRC32HashBuilder;
import ro.isdc.wro.model.resource.support.hash.HashStrategy;


/**
 * The simplest implementation of {@link NamingStrategy} which encodes the hash code into the name of the
 * resource. For instance: For <code>group.js</code> -> <code>group-<hashcode>.js</code>. This implementation uses by
 * default {@link CRC32HashBuilder} implementation.
 *
 * @author Alex Objelean
 * @created 15 Aug 2010
 */
public class HashEncoderNamingStrategy
  implements NamingStrategy {
  public static final String ALIAS = "hashEncoder-CRC32";
  private HashStrategy hashBuilder = newHashBuilder();


  /**
   * @return an implementation of {@link HashStrategy}.
   */
  protected HashStrategy newHashBuilder() {
    return new CRC32HashBuilder();
  }

  /**
   * {@inheritDoc}
   */
  public String rename(final String originalName, final InputStream inputStream)
    throws IOException {
    final String baseName = FilenameUtils.getBaseName(originalName);
    final String extension = FilenameUtils.getExtension(originalName);
    final String hash = hashBuilder.getHash(inputStream);
    final StringBuilder sb = new StringBuilder(baseName).append("-").append(hash);
    if (!StringUtils.isEmpty(extension)) {
      sb.append(".").append(extension);
    }
    return sb.toString();
  }
}
