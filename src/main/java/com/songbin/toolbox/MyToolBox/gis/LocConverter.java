package com.songbin.toolbox.MyToolBox.gis;


/**
 * 坐标系转化
 * @author songbin
 *
 */

public class LocConverter {

    public static final double pi = Math.PI;

    //
    // Krasovsky 1940
    //
    // a = 6378245.0, 1/f = 298.3
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    private static final double a = 6378245.0;
    private static final double ee = 0.00669342162296594323;

    /**
     * GPS坐标 转 火星坐标
     *
     * @param locs lat lng
     * @return 如果返回null，说明不在中国，否则返回修改后的locs
     */
    public static double[] wgs2gcj(double[] locs) {
        double wgLat = locs[0];
        double wgLon = locs[1];// out double mgLat, out double mgLon
        if (outOfChina(wgLat, wgLon)) {
            return null;
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        locs[0] = wgLat + dLat;
        locs[1] = wgLon + dLon;
        return locs;
    }

    public static boolean outOfChina(double lat, double lon) {
        return lon < 72.004 || lon > 137.8347 || lat < 0.8293 || lat > 55.8271;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 火星坐标转GPS
     * 没有直接算法
     * 用迭代的方法
     * 超强迭代，只调用几次 wgs2gcj 即可
     *
     * @param locs
     * @return 如果返回null，说明有问题，否则返回修改后的locs
     */
    public static double[] gcj2wgs(double[] locs) {
        double a = locs[0];
        double b = locs[1];
        locs = wgs2gcj(locs);
        if (locs == null) {
            return locs;
        }
        double c = 2 * a - locs[0];
        double d = 2 * b - locs[1];
        locs[0] = c;
        locs[1] = d;
        int i;
        for (i = 0; i < 4; i++) {
            locs = wgs2gcj(locs);
            if (locs == null) {
                return locs;
            }
            double gap = Math.abs(locs[0] - a) + Math.abs(locs[1] - b);
            locs[0] = c;
            locs[1] = d;
            if (gap < 1e-6) {
                return locs;
            }
            locs = search(a, locs, 0);
            if (locs == null) {
                return locs;
            }
            locs = search(b, locs, 1);
            if (locs == null) {
                return null;
            }
            c = locs[0];
            d = locs[1];
        }
        if (i >= 4) {
            System.out.println("warn: max loop used");
        }
        return locs;
    }

    private static double[] search(double a, double[] locs, int pos) {
        double c = locs[pos];
        double low = c - 2;
        double high = c + 2;
        double k = locs[1 - pos];
        double mid = low + (high - low) / 2;
        while ((high - low) >= 1e-7) {
            locs[pos] = mid;
            locs = wgs2gcj(locs);
            if (locs == null) {
                return null;
            }
            locs[1 - pos] = k;
            double v = locs[pos];
            if (v > a) {
                high = mid;
                mid = mid - (v - a) * 1.01;
                if (mid <= low) {
                    mid = low + (high - low) / 2;
                }
            } else if (v < a) {
                low = mid;
                mid = mid + (a - v) * 1.01;
                if (mid > high) {
                    mid = low + (high - low) / 2;
                }
            } else {
                locs[pos] = mid;
                return locs;
            }
        }
        locs[pos] = low + (high - low) / 2;
        return locs;
    }

    private static final double x_pi = Math.PI * 3000.0 / 180.0;

    /**
     * 火星坐标转百度坐标
     *
     * @param locs 返回locs，直接修改自参数
     */
    public static double[] gcj2bd(double[] locs) {
        double x = locs[1], y = locs[0];
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        locs[1] = z * Math.cos(theta) + 0.0065;
        locs[0] = z * Math.sin(theta) + 0.006;
        return locs;
    }

    /**
     * 百度坐标转火星坐标
     *
     * @param locs 返回locs，直接修改自参数
     */
    public static double[] bd2gcj(double[] locs) {
        double x = locs[1] - 0.0065, y = locs[0] - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        locs[1] = z * Math.cos(theta);
        locs[0] = z * Math.sin(theta);
        return locs;
    }

    /**
     * GPS转百度
     * 2次调用上面函数
     *
     * @param locs lat lng
     * @return 如果返回null，说明有问题，否则返回修改后的locs
     */
    public static double[] wgs2bd(double[] locs) {
        locs = wgs2gcj(locs);
        if (locs != null) {
            locs = gcj2bd(locs);
        }
        return locs;
    }

    /**
     * 百度转GPS
     * 2次调用上面函数
     *
     * @param locs lat lng
     * @return 如果返回null，说明有问题，否则返回修改后的locs
     */
    public static double[] bd2wgs(double[] locs) {
        locs = bd2gcj(locs);
        if (locs != null) {
            locs = gcj2wgs(locs);
        }
        return locs;
    }

    /**
     * 测试
     * 忽略
     */
    public static void main(String[] args) throws Exception {
        double[] locs = new double[]{39.914551, 116.4039};
        if(bd2wgs(locs)!=null) {
            System.out.println(locs[0]);
            System.out.println(locs[1]);
        }
    }

}
