
package helper;

import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.content.pm.PackageInfo;
import android.content.pm.Signature;

public class GetCerts {
    private static final String nl = "\n";

    private static final String LOG_TAG = "EntryPoint";

    public static String getPackageSignatures(PackageInfo pSigns, String signs)
            throws CertificateException, CertificateEncodingException {
        if (pSigns.signatures != null) {
            signs = "(\n";
            X509Certificate c = decodeCertificate(pSigns.signatures[pSigns.signatures.length-1]);
            PublicKey caSign = c.getPublicKey();
            for (int i = 0; i < pSigns.signatures.length; i++) {
                c = decodeCertificate(pSigns.signatures[i]);
                try {
                    c.verify(caSign);
                    signs += "Signature OK" + nl;
                } catch (InvalidKeyException e1) {
                    signs += "Error : " + e1.getMessage() + nl;
                } catch (NoSuchAlgorithmException e1) {
                    signs += "Error : " + e1.getMessage() + nl;
                } catch (NoSuchProviderException e1) {
                    signs += "Error : " + e1.getMessage() + nl;
                } catch (SignatureException e1) {
                    signs += "Error : " + e1.getMessage() + nl;
                }
                signs += i + ") " + c.getSigAlgName() + nl + "subject:" + nl + c.getSubjectDN()
                        + nl + "issuer:" + nl + c.getIssuerDN() + nl;
                {
                    MessageDigest mdSHA1 = null;
                    // This is workaround because of error in implementation
                    for (int i1 = 0; i1 < 10; i1++) {
                        try {
                            mdSHA1 = MessageDigest.getInstance("SHA1");
                            break;
                        } catch (Exception e) {
                        }
                    }
                    if (mdSHA1 != null) {
                        byte[] apkCertHash = mdSHA1.digest(c.getEncoded());
                        signs += "SHA1: " + ba2hs(apkCertHash, " ") + nl;
                    }
                }
                // {
                // MessageDigest mdMD5 = null;
                // for (int i1 = 0; i1 < 10; i1++) {
                // try {
                // mdMD5 = MessageDigest.getInstance("MD5");
                // break;
                // } catch (Exception e) {
                // }
                // }
                // if (mdMD5 != null) {
                // byte[] apkCertHash = mdMD5.digest(c.getEncoded());
                // signs += "MD5: " + ba2hs(apkCertHash, " ") + nl;
                // }
                // }
                signs += nl;
            }
            signs += ")" + nl;
        }
        return signs;
    }

    public static final String ba2hs(byte[] bytes, String spc) {
        StringBuffer sb = new StringBuffer();
        if (bytes == null) {
            sb.append("--null--");
        } else {
            for (byte b : bytes) {
                sb.append(String.format("%02x%s", b & 0xFF, spc));
            }
            if (sb.length() > 0 && !spc.equals(""))
                sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static X509Certificate decodeCertificate(Signature signatures)
            throws CertificateException {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) certFactory
                .generateCertificate(new ByteArrayInputStream(signatures.toByteArray()));
        return cert;
    }

}
