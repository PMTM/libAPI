package cz.xlinux.libAPI.libFce;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import cz.xlinux.libAPI.testAct.TestActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

public class LibAPI {

	private final static int flags = PackageManager.GET_SIGNATURES;

	public static void testCert(Context ctx, String packageName,
			TestActivity act) {
		PackageManager pm = ctx.getPackageManager();

		PackageInfo packageInfo = null;

		try {
			packageInfo = pm.getPackageInfo(packageName, flags);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Signature[] signatures = packageInfo.signatures;

		act.debugToast("Number of signatures: " + signatures.length);

		for (Signature sign : signatures) {
			byte[] cert = sign.toByteArray();

			InputStream input = new ByteArrayInputStream(cert);

			CertificateFactory cf = null;
			try {
				cf = CertificateFactory.getInstance("X509");

			} catch (CertificateException e) {
				e.printStackTrace();
				return;
			}

			X509Certificate c = null;

			try {
				c = (X509Certificate) cf.generateCertificate(input);
			} catch (CertificateException e) {
				e.printStackTrace();
				return;
			}

			act.debugToast("getSigAlgName: " + c.getSigAlgName());
			act.debugToast("getIssuerDN: " + c.getIssuerDN());
			act.debugToast("getSubjectDN: " + c.getSubjectDN());

			PublicKey key;
			// if self signed then it works
			key = c.getPublicKey();
			try {
				c.verify(key);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
				return;
			} catch (CertificateException e) {
				e.printStackTrace();
				return;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return;
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
				return;
			} catch (SignatureException e) {
				e.printStackTrace();
				return;
			}
			act.debugToast("Signature verified");

			try {
				MessageDigest md = MessageDigest.getInstance("SHA1");
				byte[] publicKey = md.digest(c.getPublicKey().getEncoded());

				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < publicKey.length; i++) {
					String appendString = Integer
							.toHexString(0xFF & publicKey[i]);
					if (appendString.length() == 1)
						hexString.append("0");
					hexString.append(appendString);
				}

				act.debugToast("Cert hash: " + hexString.toString());

			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
				return;
			}
		}
	}

	public static String testCertPkg(PackageManager pm, String packageName) {

		String ret = "";
		PackageInfo packageInfo = null;

		try {
			packageInfo = pm.getPackageInfo(packageName, flags);
		} catch (NameNotFoundException e) {
			return "no package info";
		}
		Signature[] signatures = packageInfo.signatures;

		ret += "Number of signatures: " + signatures.length;

		for (Signature sign : signatures) {
			byte[] cert = sign.toByteArray();

			InputStream input = new ByteArrayInputStream(cert);

			CertificateFactory cf = null;
			try {
				cf = CertificateFactory.getInstance("X509");
			} catch (CertificateException e) {
				return ret + "\n" + e.getMessage();
			}

			X509Certificate c = null;

			try {
				c = (X509Certificate) cf.generateCertificate(input);
			} catch (CertificateException e) {
				return ret + "\n" + e.getMessage();
			}

			ret += "\ngetSigAlgName: " + c.getSigAlgName();
			ret += "\ngetIssuerDN: " + c.getIssuerDN();
			ret += "\ngetSubjectDN: " + c.getSubjectDN();

			PublicKey key;
			// if self signed then it works
			key = c.getPublicKey();
			try {
				c.verify(key);
			} catch (InvalidKeyException e) {
				return ret + "\n" + e.getMessage();
			} catch (CertificateException e) {
				return ret + "\n" + e.getMessage();
			} catch (NoSuchAlgorithmException e) {
				return ret + "\n" + e.getMessage();
			} catch (NoSuchProviderException e) {
				return ret + "\n" + e.getMessage();
			} catch (SignatureException e) {
				return ret + "\n" + e.getMessage();
			}
			ret += "\nSignature verified";

			try {
				MessageDigest md = MessageDigest.getInstance("SHA1");
				byte[] publicKey = md.digest(c.getPublicKey().getEncoded());

				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < publicKey.length; i++) {
					String appendString = Integer
							.toHexString(0xFF & publicKey[i]);
					if (appendString.length() == 1)
						hexString.append("0");
					hexString.append(appendString);
				}

				ret+="\nCert hash: " + hexString.toString();

			} catch (NoSuchAlgorithmException e1) {
				return ret + "\n" + e1.getMessage();
			}
		}
		return ret;
	}
}
