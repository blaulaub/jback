/**
 * This package solely contains an abstract security model, i.e., only interfaces.
 * <p>
 * The sole intention is to layout security relations: What are the fundamental entities, both in the
 * business domain and in the security domain.
 * <p>
 * In this model, it turned out, the security domain is on top of the business domain (not next to, below, or in it).
 * The business domain is security-unaware (but has a concept of (unprotected) privileges).
 * <p>
 * In the business domain there are {@link ch.patchcode.jback.secModel.Role}s,
 * {@link ch.patchcode.jback.secModel.Person}s, {@link ch.patchcode.jback.secModel.Organisation}s and
 * {@link ch.patchcode.jback.secModel.Privilege}s.
 * <p>
 * In the security domain there are {@link ch.patchcode.jback.secModel.User}s,
 * {@link ch.patchcode.jback.secModel.Principal}s and {@link ch.patchcode.jback.secModel.AuthenticationMean}s.
 */
package ch.patchcode.jback.secModel;
