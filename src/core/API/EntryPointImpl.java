
package core.API;

import helper.GetCerts;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

import aidl.core.API.OnCouponChange;
import aidl.core.API.OnNewHistoryItem;
import aidl.core.API.OnNewReceipt;
import aidl.core.API.OnTicketChange;
import aidl.core.API.SecurityWatchdog;

import aidl.core.API.AbstractFunInterface;
import aidl.core.API.AbstractJoyInterface;
import aidl.core.API.AbstractBestInterface;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

public class EntryPointImpl extends aidl.core.API.EntryPoint.Stub {

    private static final String LOG_TAG = "EntryPointImpl";

    private static SecurityWatchdog wd;

    private static OnCouponChange couponCB;

    private static OnNewHistoryItem histItemCB;

    private static OnTicketChange ticketCB;

    private static OnNewReceipt receiptCB;

    private static EntryPoint serviceLink;


    public EntryPointImpl(EntryPoint entryPoint) {
        EntryPointImpl.serviceLink = entryPoint;
    }

    @Override
    public SecurityWatchdog getSecurityWatchdog() throws RemoteException {
        Log.d(LOG_TAG, "getSecurityWatchdog called");
        String callingApp = serviceLink.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.d(LOG_TAG, "callingApp = " + callingApp);

        PackageInfo pSigns;
        try {
            pSigns = serviceLink.getPackageManager().getPackageInfo(callingApp,
                    PackageManager.GET_SIGNATURES);
            String signs = "";
            signs = GetCerts.getPackageSignatures(pSigns, signs);
            Log.d(LOG_TAG, "signs = " + signs);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (wd == null)
            wd = new SecurityWatchdogImpl(serviceLink);
        return wd;
    }

    @Override
    public OnCouponChange getCouponCB() throws RemoteException {
        Log.d(LOG_TAG, "getCouponCB called");
        // if (serviceLink != null) {
        // serviceLink.sendMessage("send message from getCouponCB()");
        // }
        if (couponCB == null)
            couponCB = new OnCouponChangeImpl(serviceLink);
        return couponCB;
    }

    @Override
    public OnNewHistoryItem getHistoryCB() throws RemoteException {
        Log.d(LOG_TAG, "getHistoryCB called");
        if (histItemCB == null)
            histItemCB = new OnNewHistoryItemImpl(serviceLink);
        return histItemCB;
    }

    @Override
    public OnTicketChange getTicketCB() throws RemoteException {
        Log.d(LOG_TAG, "getTicketCB called");
        if (ticketCB == null)
            ticketCB = new OnTicketChangeImpl(serviceLink);
        return ticketCB;
    }

    @Override
    public OnNewReceipt getReceiptCB() throws RemoteException {
        Log.d(LOG_TAG, "getReceiptCB called");
        if (receiptCB == null)
            receiptCB = new OnNewReceiptImpl(serviceLink);
        return receiptCB;
    }

    @Override
    public AbstractFunInterface getFunAPI(String verId) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractJoyInterface getJoyAPI(String verId) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractBestInterface getBestAPI(String verId) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

}
