#!/bin/sh
# Copyright 2013 Stephen L Arnold (stephen.arnold42 _AT_ gmail.com)
# Distributed under the terms of the GNU General Public License v2
#
# /usr/bin/next_mpd_playlist.sh
# A next-playlist wrapper for mpc that maintains a volatole per-session state
# and resets to the first playlist on reboot (ignores playlist named all).

PATH=/bin:/sbin:/usr/bin:/usr/sbin

PLAYLISTS=$(mpc lsplaylists | sort | xargs echo | sed "s/all //")

if [ -z $PLAYLISTS ] ; then
    echo "No playlists defined!"
    exit 1
fi

function do_next() {
    CURRENT_INDEX=/tmp/current_playlist_index
    SIZE="$#"
    let "MAX = SIZE - 1"

    if [ -f ${CURRENT_INDEX} ] ; then
        CURRENT=$(cat "${CURRENT_INDEX}")
    else
        CURRENT="$MAX"
        echo "${CURRENT}" > ${CURRENT_INDEX}
    fi

    declare -a pls=("$@")
    let "NEXT = CURRENT + 1"
    if [ $NEXT -gt $MAX ] ; then
        NEXT="0"
    fi

    echo "$MAX $CURRENT $NEXT ${pls[${NEXT}]}"
    mpc stop
    mpc clear
    mpc load "${pls[${NEXT}]}"
    mpc play
}

do_next ${PLAYLISTS}

if [ $? -eq 0 ]; then
    echo "${NEXT}" > ${CURRENT_INDEX}
fi
