package aidl.core.API;

import aidl.sp.API.Coupon;

interface OnCouponChange {

	void addCoupon(in Coupon item);
	void removeCoupon(in Coupon item);
}