/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Helper;


import android.content.Context;

import androidx.annotation.NonNull;

import com.qualcomm.ftccommon.SoundPlayer;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions.DeferredActionType;

import java.io.File;
import java.util.List;


public class Sound {
    // Point to sound files on the phone's drive
    private String soundPath = "/FIRST/blocks/sounds";
    private File duckFile = new File("/sdcard" + soundPath + "/duck.mp3");
    private File STFFile = new File("/sdcard" + soundPath + "/STF.mp3");
    boolean duckFound = duckFile.exists();
    boolean STFFound = STFFile.exists();
    private Context map;

    public Sound (@NonNull Context hard){
        this.map = hard;
    }

    public void playQuack() {
        if (duckFile.exists()) {
            SoundPlayer.getInstance().startPlaying(map, duckFile);

        }
    }

    public void playSTF() {
        if(STFFile.exists()) {
            SoundPlayer.getInstance().startPlaying(map, STFFile);
        }
    }

    public void processActions(){
        DeferredActions.CreateDeferredAction(5000, DeferredActionType.PLAY_SOUND);
    }

}
