DESCRIPTION = "A custom image based on core-image and x11."

# this shouldn't be required, but seems to be now...
include rpi-hwup-image.bb

# core-image bbclass provides core-boot and base packages
inherit core-image

IMAGE_FEATURES += "package-management ssh-server-openssh splash \
    x11-base hwcodecs"

# you can override the following three parameters in your local.conf
DEFAULT_TIMEZONE ?= "Universal"

# possible values are 1 (DVI only) or 2 (hdmi with audio)
DEFAULT_AUDIO_OUT ?= "2"

# possible values are internal or external
CUSTOM_FEED_CONFIG ?= "external"

# didn't work, so I have to make my own implementation later...
#ROOTLESS_X = "1"

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    ${CORE_IMAGE_EXTRA_INSTALL} \
    ${EXTRA_TIMEZONES} \
    ${XSERVER} \
    ${WINDOW_MANAGER} \
    ${DEJAVU_FONTS} \
    ${DESKTOP_APPS} \
    vc-graphics-hardfp \
    bcm2835-tests \
    linux-raspberrypi-firmware \
    packagegroup-base-vfat \
    encodings \
    bash \
    nano \
    zram \
    rsync \
    screen \
    gkrellm \
    cpufrequtils \
    python-shell \
    packagegroup-base-alsa \
    alsa-utils-speakertest \
    alsa-utils-amixer \
    mpd \
    mpc \
    empcd \
    mc \
    vim \
    file \
    links \
    irssi \
    tzdata \
    ntp \
    ntpdate \
    ntp-utils \
    perl-module-socket \
    perl-module-getopt-std \
    usbutils \
    sysfsutils \
    findutils \
    dosfstools \
    nfs-utils \
    cifs-utils \
    ntfsprogs \
    git \
    lirc \
    lirc-exec \
    lirc-remotes \
    lirc-x \
    bluez-hcidump \
    bluez4-agent \
    wpa-supplicant \
    jack-server \
    jack-utils \
"

XSERVER = " \
    packagegroup-core-x11 \
    packagegroup-core-x11-base \
    packagegroup-fonts-truetype-core \
    libxi \
    libxtst \
    libx11-locale \
    xorg-minimal-fonts \
    gdk-pixbuf-loader-ico \
    gdk-pixbuf-loader-bmp \
    gdk-pixbuf-loader-ani \
    gdk-pixbuf-xlib \
    liberation-fonts \
    xkbcomp \
    xmodmap \
    xrdb \
    setxkbmap \
    xterm \
"    

DESKTOP_APPS = " \
    imagemagick \
    feh \
    mupdf \
    xchat \
    midori \
    vala-terminal \
    leafpad \
    leafpad-stock-icons \
    pidgin \
    gnome-bluetooth \
    pcmanfm \
    xfmpc \
    gkrellm-client \
    gst-meta-video \
    gst-meta-audio \
    imlib2-tests \
    imlib2-themes \
"

WINDOW_MANAGER = " \
    openbox \
    openbox-config \
    openbox-core \
    openbox-theme-bear2 \
    openbox-theme-clearlooks \
    openbox-theme-onyx \
    openbox-backgrounds \
    libcanberra-gtk2 \
    gtk-theme-clearlooks \
    gtk-theme-crux \
    gtk-theme-mist \
    clearlooks-theme-enable \
    gnome-icon-theme \
    gnome-icon-theme-enable \
    hicolor-icon-theme \
    pyxdg \
    xdg-utils \
    obconf \
    tint2 \
"

DEJAVU_FONTS = " \
    ttf-dejavu-sans \
    ttf-dejavu-sans-condensed \
    ttf-dejavu-sans-mono \
    ttf-dejavu-serif \
    ttf-dejavu-serif-condensed \
"

EXTRA_TIMEZONES = " \
    tzdata-africa \
    tzdata-americas \
    tzdata-antarctica \
    tzdata-arctic \
    tzdata-asia \
    tzdata-atlantic \
    tzdata-australia \
    tzdata-europe \
    tzdata-misc \
    tzdata-pacific \
    tzdata-posix \
    tzdata-right \
"
