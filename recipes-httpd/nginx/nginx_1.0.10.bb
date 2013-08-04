DESCRIPTION = "HTTP and reverse proxy server"
HOMEPAGE = "http://nginx.org/"
LICENSE = "BSD"
SECTION = "net"
PRIORITY = "optional"
DEPENDS = "libpcre gzip openssl"
PR = "r2"

SRC_URI = " \
	http://nginx.org/download/nginx-${PV}.tar.gz \
	http://www.grid.net.ru/nginx/download/nginx_upload_module-2.2.0.tar.gz;name=uploadmod \
	file://nginx-cross_${PV}.diff;name=crosspatch \
	file://nginx.conf \
	file://nginx.init \
"

S = "${WORKDIR}/nginx-${PV}"

inherit autotools useradd update-rc.d

SRC_URI[md5sum] = "17da4802209b83d9bebb0f0edd975dfc"
SRC_URI[sha256sum] = "1daf3950623c90b084e7eceb104071596060aca5c721bf890549fc2990b1ebe6"
SRC_URI[uploadmod.md5sum] = "2681a6167551830a23336fa41bc539a1"
SRC_URI[uploadmod.sha256sum] = "b1c26abe0427180602e257627b4ed21848c93cc20cefc33af084983767d65805"
SRC_URI[crosspatch.md5sum] = "8703582cc0747248878f088ff860f699"
SRC_URI[crosspatch.sha256sum] = "a17f478de1e0e1e9a879511c9a14b322601abe7b2f4be12f462335998d0a8513"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b06b69c53c7ac41d8d6698957ed6a71b"

CONFFILES_${PN} = "${sysconfdir}/nginx/nginx.conf \
					${sysconfdir}/nginx/fastcgi.conf\
					${sysconfdir}/nginx/fastcgi_params \
					${sysconfdir}/nginx/koi-utf \
					${sysconfdir}/nginx/koi-win \
					${sysconfdir}/nginx/mime.types \
					${sysconfdir}/nginx/scgi_params \
					${sysconfdir}/nginx/uwsgi_params \
					${sysconfdir}/nginx/win-utf \
"

INITSCRIPT_NAME = "nginx"
INITSCRIPT_PARAMS = "defaults 92 20"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --home ${localstatedir}/www \
                       --no-create-home --shell /bin/false \
                       --user-group www"

do_configure () {
	PTRSIZE=$(expr ${SITEINFO_BITS} / 8)

	echo $CFLAGS
	echo $LDFLAGS

	./configure \
	--crossbuild=Linux:${TUNE_ARCH} \
	--with-endian=${@base_conditional('SITEINFO_ENDIANNESS', 'le', 'little', 'big', d)} \
	--with-int=4 \
	--with-long=${PTRSIZE} \
	--with-long-long=8 \
	--with-ptr-size=${PTRSIZE} \
	--with-sig-atomic-t=${PTRSIZE} \
	--with-size-t=${PTRSIZE} \
	--with-off-t=${PTRSIZE} \
	--with-time-t=${PTRSIZE} \
	--with-sys-nerr=132 \
	--conf-path=/etc/nginx/nginx.conf \
	--http-log-path=/var/log/nginx/access.log \
	--error-log-path=/var/log/nginx/error.log \
	--pid-path=/var/run/nginx/nginx.pid \
	--prefix=/usr \
	--with-http_ssl_module \
	--with-http_gzip_static_module \
	--add-module=../nginx_upload_module-2.2.0
}

do_install_append () {
    install -d ${D}${localstatedir}/www
    mv ${D}/usr/html ${D}${localstatedir}/www/
    chown www:www -R ${D}${localstatedir}/www
    chmod g+w -R ${D}/${localstatedir}/www

    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/nginx
    install -m 0755 ${WORKDIR}/nginx.init ${D}${sysconfdir}/init.d/nginx
    install -m 0644 ${WORKDIR}/nginx.conf ${D}${sysconfdir}/nginx/

    install -d ${D}${sysconfdir}/default/volatiles
    echo "d www www 0755 ${localstatedir}/run/nginx none" \
        > ${D}${sysconfdir}/default/volatiles/99_nginx
}

FILES_${PN} += "${localstatedir}/ /run/"
