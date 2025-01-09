<template>
  <div class="play-bar" :class="{ show: toggle1 }">
    <div @click="toggle1 = !toggle1" class="item-up" :class="{ turn: toggle1 }">
      <svg
        t="1730706000260"
        class="icon"
        viewBox="0 0 1024 1024"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
        p-id="8049"
        width="25"
        height="25"
      >
        <path
          d="M512 960c-247.039484 0-448-200.960516-448-448S264.960516 64 512 64 960 264.960516 960 512 759.039484 960 512 960zM512 128c-211.744443 0-384 172.255557-384 384s172.255557 384 384 384 384-172.255557 384-384S723.744443 128 512 128z"
          fill="#d0d0d0"
          p-id="8050"
        ></path>
        <path
          d="M694.559548 522.144013c-12.54369-12.607338-33.375299-12.640022-45.951673-0.063647l-104.608735 103.903454L543.99914 319.327402c0-17.759333-14.208843-32.160839-32.00086-32.160839-17.759333 0-32.00086 14.399785-32.00086 32.160839l0 308.319849-105.215966-106.688456c-12.480043-12.607338-32.704421-12.736353-45.311759-0.25631-12.640022 12.512727-12.672705 32.895364-0.192662 45.504421l159.359226 161.055342c6.271845 6.335493 14.592447 9.567746 22.880366 9.567746 8.160624 0 16.383174-3.168606 22.624056-9.311437 0.032684-0.063647 0.032684-0.063647 0.063647-0.127295 0.032684 0 0.063647 0 0.096331-0.063647l160.191802-159.679183C707.072275 555.104744 707.103239 534.720387 694.559548 522.144013z"
          fill="#d0d0d0"
          p-id="8051"
        ></path>
      </svg>
    </div>
    <div class="kongjian">
      <!-- 上一首 -->
      <div class="item">
        <svg
          t="1730166365300"
          class="icon"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="4268"
          width="26"
          height="26"
        >
          <path
            @click="prev"
            style="cursor: pointer"
            d="M364.302083 465.602819L687.954365 218.588294c38.416414-29.327534 93.791393-1.929039 93.791392 46.396277v494.029051c0 48.325316-55.374979 75.725617-93.791392 46.398084L364.302083 558.397181c-30.600916-23.357989-30.600916-69.436372 0-92.794362zM238.945254 780.798397V451.684117v-164.562559c0-19.628152-5.904521-60.475733 17.057907-75.841215 25.523642-17.068744 59.747828 1.210165 59.747828 31.919454v493.676839c0 19.628152 5.915358 60.473927-17.047069 75.841215-25.53448 17.068744-59.758666-1.211971-59.758666-31.919454z"
            fill="#231815"
            p-id="4269"
          ></path>
        </svg>
      </div>

      <!-- 播放-->
      <div class="item" @click="togglePlay">
        <!-- v-show 指令，它只是显示或隐藏元素而不移除它们 -->
        <!-- v-if是移除 -->
        <div class="item" v-show="!this.isplay">
          <svg
            t="1730166434122"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="5285"
            width="26"
            height="26"
          >
            <path
              style="cursor: pointer"
              d="M832.0064 512 192 896 192 128 832.0064 512 832.0064 512zM832.0064 512"
              fill="#272636"
              p-id="5286"
            ></path>
          </svg>
        </div>
        <div class="item" v-show="this.isplay">
          <svg
            t="1730173342198"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="13180"
            width="26"
            height="26"
          >
            <path
              style="cursor: pointer"
              d="M320 128A64 64 0 0 0 256 192v640a64 64 0 0 0 128 0v-640A64 64 0 0 0 320 128z m384 0A64 64 0 0 0 640 192v640a64 64 0 0 0 128 0v-640A64 64 0 0 0 704 128z"
              fill="#272636"
              p-id="13181"
            ></path>
          </svg>
        </div>
      </div>

      <!-- 下一首-->
      <div class="item" style="transform: scaleX(-1)">
        <svg
          t="1730166365300"
          class="icon"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="4268"
          width="26"
          height="26"
        >
          <path
            @click="next"
            style="cursor: pointer"
            d="M364.302083 465.602819L687.954365 218.588294c38.416414-29.327534 93.791393-1.929039 93.791392 46.396277v494.029051c0 48.325316-55.374979 75.725617-93.791392 46.398084L364.302083 558.397181c-30.600916-23.357989-30.600916-69.436372 0-92.794362zM238.945254 780.798397V451.684117v-164.562559c0-19.628152-5.904521-60.475733 17.057907-75.841215 25.523642-17.068744 59.747828 1.210165 59.747828 31.919454v493.676839c0 19.628152 5.915358 60.473927-17.047069 75.841215-25.53448 17.068744-59.758666-1.211971-59.758666-31.919454z"
            fill="#231815"
            p-id="4269"
          ></path>
        </svg>
      </div>

      <!-- 歌曲图片 -->
      <div class="item-img">
        <img :src="picUrl" @click="toLyric" style="cursor: pointer" />
      </div>

      <!-- 播放进度 -->
      <div class="playing-speed">
        <!-- 播放开始时间 -->
        <div class="current-time">{{ nowTime }}</div>
        <div class="progress-box">
          <div class="item-song-title">
            <div>{{ this.title }}</div>
            <div>{{ this.artist }}</div>
          </div>
          <!-- mousemove鼠标移动 mouseup鼠标松开  mousedown鼠标按下-->
          <div class="progress" ref="progress" @mousemove="mousemove">
            <!-- 进度条 动态绑定宽度为30%-->
            <div ref="bg" class="bg" @click="updatemove">
              <div
                ref="curProgress"
                class="cur-progress"
                :style="{ width: curLength + '%' }"
              ></div>
              <!-- 推动的点 -->
              <div
                ref="idot"
                class="idot"
                :style="{ left: curLength + '%' }"
                @mousedown="mousedown"
                @mouseup="mouseup"
              ></div>
            </div>
          </div>
        </div>

        <!-- 播放结束时间 -->
        <div class="left-time">{{ songTime }}</div>

        <!-- 音量 -->
        <div class="item">
          <svg
            v-if="volume == 0"
            t="1730369885468"
            class="icon item-volume"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="7673"
            width="21"
            height="21"
          >
            <path
              style="cursor: pointer"
              @click="JingYin"
              d="M252.4672 342.272H139.6224A37.3248 37.3248 0 0 0 102.4 379.8016v264.2944c0 20.7872 16.6912 37.632 37.2224 37.632h111.9232v0.1536l273.5616 179.456c6.5024 5.632 14.9504 9.0112 24.1664 9.0112a37.4784 37.4784 0 0 0 36.864-42.8032V196.4032a37.4784 37.4784 0 0 0-36.864-42.8032 36.8128 36.8128 0 0 0-22.528 7.68L252.416 342.272zM830.0032 512l83.712-84.8896a27.9552 27.9552 0 0 0-0.1024-39.0144 26.9312 26.9312 0 0 0-38.5024-0.1024l-83.7632 84.8384-83.8144-84.8384a26.9312 26.9312 0 0 0-38.5024 0.1024 27.9552 27.9552 0 0 0-0.1024 39.0144L752.64 512l-83.7632 84.8896a27.9552 27.9552 0 0 0 0.1024 39.0144c10.752 10.8544 27.904 10.8032 38.5024 0.1024l83.8144-84.8384 83.7632 84.8384c10.5984 10.752 27.7504 10.752 38.5024-0.1024a27.9552 27.9552 0 0 0 0.1024-39.0144L829.952 512z"
              fill="#000000"
              p-id="7674"
            ></path>
          </svg>

          <svg
            v-else
            t="1730168063274"
            class="icon item-volume"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="7136"
            width="21"
            height="21"
          >
            <path
              style="cursor: pointer"
              @click="YouSheng"
              d="M260.256 356.576l204.288-163.968a32 32 0 0 1 52.032 24.96v610.432a32 32 0 0 1-51.968 24.992l-209.92-167.552H96a32 32 0 0 1-32-32v-264.864a32 32 0 0 1 32-32h164.256zM670.784 720.128a32 32 0 0 1-44.832-45.664 214.08 214.08 0 0 0 64.32-153.312 213.92 213.92 0 0 0-55.776-144.448 32 32 0 1 1 47.36-43.04 277.92 277.92 0 0 1 72.416 187.488 278.08 278.08 0 0 1-83.488 198.976zM822.912 858.88a32 32 0 1 1-45.888-44.608A419.008 419.008 0 0 0 896 521.152c0-108.704-41.376-210.848-114.432-288.384a32 32 0 0 1 46.592-43.872c84.16 89.28 131.84 207.04 131.84 332.256 0 127.84-49.76 247.904-137.088 337.728z"
              fill="#000000"
              p-id="7137"
            ></path>
          </svg>
          <!-- Slider 滑块 vertical垂直的-->
          <el-slider
            class="volume"
            v-model="volume"
            :vertical="true"
          ></el-slider>
        </div>

        <!-- 收藏 -->
        <div class="item">
          <svg
            t="1730168250043"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="8137"
            width="19"
            height="19"
          >
            <path
              @click="collection"
              ref="collectCang"
              :class="{ ChouActive: isActive }"
              class="ChouActive-prev"
              style="cursor: pointer"
              d="M512.002047 913.854391l-62.554708-61.180408c-232.28846-200.949708-384.157333-336.436472-384.157333-502.446413 0-135.416156 107.175975-240.298901 245.699911-240.298901 75.902714 0 151.86785 34.997072 201.01213 91.76703 49.072649-56.769958 125.104299-91.76703 201.007013-91.76703 138.453329 0 245.699911 104.883769 245.699911 240.298901 0 166.009941-151.935388 301.496705-384.219755 502.446413L512.002047 913.854391z"
              p-id="8138"
            ></path>
          </svg>
        </div>

        <!-- 下载 -->
        <div class="item">
          <svg
            t="1730168284685"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="9170"
            width="23"
            height="23"
          >
            <path
              @click="download"
              style="cursor: pointer"
              d="M795.694545 395.636364a289.047273 289.047273 0 0 0-567.38909 0 197.585455 197.585455 0 0 0 18.385454 395.636363h30.952727a23.272727 23.272727 0 0 0 0-46.545454H246.690909a151.272727 151.272727 0 1 1-2.327273-302.545455l23.272728 5.352727 3.025454-25.6a242.269091 242.269091 0 0 1 480.814546 0l4.654545 25.134546 23.272727-4.887273a151.272727 151.272727 0 1 1-2.327272 302.545455h-34.909091a23.272727 23.272727 0 0 0 0 46.545454h35.141818a197.585455 197.585455 0 0 0 18.385454-395.636363z"
              p-id="9171"
            ></path>
            <path
              d="M628.363636 689.803636l-91.927272 93.090909v-286.254545a23.272727 23.272727 0 0 0-46.545455 0v285.090909l-91.927273-93.090909A23.272727 23.272727 0 1 0 365.149091 721.454545l131.490909 131.723637a23.272727 23.272727 0 0 0 33.047273 0L661.178182 721.454545A23.272727 23.272727 0 1 0 628.363636 689.803636z"
              p-id="9172"
            ></path>
          </svg>
        </div>

        <!-- 当前播放的歌曲列表 -->
        <div class="item" style="cursor: pointer" @click="changeAside">
          <svg
            t="1730168334416"
            class="icon"
            viewBox="0 0 1106 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="10354"
            width="15"
            height="15"
          >
            <path
              d="M1021.238317 0.001202a84.160991 84.160991 0 0 1 84.581796 84.581796v64.383158a84.160991 84.160991 0 0 1-84.581796 84.581796H84.586605A84.160991 84.160991 0 0 1 0.004809 148.966156V84.582998A84.160991 84.160991 0 0 1 84.586605 0.001202z m0 395.195967a84.160991 84.160991 0 0 1 84.581796 84.581796v64.383158a84.160991 84.160991 0 0 1-84.581796 84.64191H84.586605A84.160991 84.160991 0 0 1 0.004809 544.222238V479.83908a84.160991 84.160991 0 0 1 84.581796-84.581796z m0 395.195967a84.160991 84.160991 0 0 1 84.581796 84.581795v64.383158a84.160991 84.160991 0 0 1-84.581796 84.581796H84.586605A84.160991 84.160991 0 0 1 0.004809 939.358089v-64.383158a84.160991 84.160991 0 0 1 84.581796-84.581795z"
              fill="#2c2c2c"
              p-id="10355"
            ></path>
          </svg>

          <!-- transition(过度) element组件里的-->
          <transition name="slide-fade">
            <div class="the-aside" v-if="showAside">
              <p class="title">播放队列</p>
              <ul class="menus">
                <li
                  class="menus-li"
                  v-for="(item, index) in listOfSongs"
                  :key="index"
                  :class="{ 'is-play': id == item.id }"
                  @click="
                    toplay(
                      item.id,
                      item.url,
                      item.pic,
                      index,
                      item.name,
                      item.lyric
                    )
                  "
                >
                  <span class="the-aside-index">
                    {{ index + 1 }}
                  </span>
                  <span class="the-aside-title">{{
                    replaceFName(item.name)
                  }}</span>
                  <span class="the-aside-name">{{
                    replaceLName(item.name)
                  }}</span>
                  <span class="the-aside-time">{{ item.time }}</span>
                </li>
              </ul>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixin } from "../mixins";
import {
  download,
  setCollect,
  getCollectOfUserId,
  deleteCollect,
} from "../api/index";

export default {
  mixins: [mixin],
  name: "play-bar",
  data() {
    return {
      nowTime: "00:00", //当前时间
      songTime: "00:00", //当前歌曲总时间
      curLength: 0, //进度条的位置
      progressLength: 0, //进度条总长度
      mouseStartX: 0, //拖拽开始位置
      tag: false, //拖动开始结束的标志
      volume: 50, //音量
      volumeYou: 0,
      toggle1: true, //显示隐藏播放器页面
    };
  },

  computed: {
    //  ...mapGetters([])，可以将指定的 getters 映射到当前组件中。
    ...mapGetters([
      "id",
      "url",
      "url1", //测试的url
      "isplay",
      "picUrl",
      "artist",
      "title",
      "duration1",
      "curTime",
      "showAside",
      "listOfSongs",
      "listIndex",
      "autoNext",
      "loginIn",
      "userId",
      "isActive",
    ]),
  },

  watch: {
    // isplay()是 mapGetters(["isplay"]) 映射到当前组件中的
    curTime() {
      this.nowTime = this.formatSeconds(this.curTime);
      this.songTime = this.formatSeconds(this.duration1);
      this.curLength = (this.curTime / this.duration1) * 100;
    },

    //音量变化
    volume() {
      this.$store.commit("setVolume", this.volume);
    },

    //
    autoNext() {
      this.next();
    },

    //收藏
    // isActive() {
    //   if (this.isActive) {
    //     this.$refs.collectCang.style.fill = "red";
    //   } else {
    //     this.$refs.collectCang.style.fill = "#272636";
    //   }
    // },
  },

  //加载页面后
  mounted() {
    //.getBoundingClientRect() 是一个 DOM 元素的方法，用于获取元素相对于视口的位置信息（包括宽度、高度、左边距、上边距等）
    // 计算距离长度(歌曲的中长度css里面的width)
    this.progressLength = this.$refs.progress.getBoundingClientRect().width;

    // 查询元素(class里的)     addEventListener 添加事件监听器
    //click点击事件，function (e) 匿名函数，classList向一个元素的 class 属性添加新的类名
    //stopPropagation(传播)（阻止事件向上（父组件）冒泡）
    // false点击之后执行   true向下执行
    //mouseover  鼠标悬停事件
    document.querySelector(".item-volume").addEventListener(
      "mouseover",
      function (e) {
        // console.log("Volume button clicked");
        document.querySelector(".volume").classList.add("show-volume");
        e.stopPropagation();
      },
      false
    );

    //防止点击到父组件
    document.querySelector(".volume").addEventListener(
      "click",
      function (e) {
        e.stopPropagation();
      },
      false
    );

    //在音乐音量条之外的地方监听点击事件
    document.addEventListener(
      "click",
      function () {
        document.querySelector(".volume").classList.remove("show-volume");
      },
      true
    );
  },

  methods: {
    YouSheng() {
      this.volumeYou = this.volume;
      this.volume = 0;
    },
    JingYin() {
      this.volume = this.volumeYou;
    },

    //控制音乐播放暂定
    togglePlay() {
      if (this.isplay) {
        this.$store.commit("setIsPlay", false);
      } else {
        this.$store.commit("setIsPlay", true);
        this.nowTime = this.formatSeconds(this.curTime);
        this.songTime = this.formatSeconds(this.duration1);
        this.curLength = (this.curTime / this.duration1) * 100;
      }
    },

    // //解释时间 format(格式)
    // formatSeconds(value) {
    //   // parseInt格式转换
    //   let theTime = parseInt(value);
    //   let result = "";
    //   let hour = parseInt(theTime / 3600);
    //   let minute = parseInt((theTime / 60) % 60);
    //   let second = parseInt(theTime % 60);
    //   if (hour > 0) {
    //     if (hour < 10) {
    //       result = "0" + hour + ":";
    //     } else {
    //       result = hour + ":";
    //     }
    //   }
    //   if (minute > 0) {
    //     if (minute < 10) {
    //       result += "0" + minute + ":";
    //     } else {
    //       result += minute + ":";
    //     }
    //   } else {
    //     result += "00:";
    //   }
    //   if (second > 0) {
    //     if (second < 10) {
    //       result += "0" + second;
    //     } else {
    //       result += second;
    //     }
    //   } else {
    //     result += "00";
    //   }
    //   return result;
    // },

    //拖拽开始
    mousedown(e) {
      // 在鼠标按下时，记录鼠标的起始水平位置（clientX）。
      this.mouseStartX = e.clientX;
      this.tag = true;
    },

    //拖拽中
    mousemove(e) {
      if (!this.duration1) {
        return false;
      }
      if (this.tag) {
        let movementX = e.clientX - this.mouseStartX; //移动的距离
        // console.log("移动距离：" + movementX);
        let curLength = this.$refs.curProgress.getBoundingClientRect().width; //curProgres的盒子的宽度
        //初始化音乐时重新计算总长度（防止首次加载页面时 没有加载音乐）
        this.progressLength = this.$refs.progress.getBoundingClientRect().width;
        // console.log("目前的拖动点距离：" + movementX);
        let newPercent = ((movementX + curLength) / this.progressLength) * 100;
        if (newPercent > 100) {
          newPercent = 100;
        }
        this.curLength = newPercent;
        // console.log("left：" + newPercent);
        this.mouseStartX = e.clientX;
        this.changeTime(newPercent);
      }
    },

    //拖拽结束
    mouseup() {
      this.tag = false;
    },

    //更改歌曲进度
    changeTime(percent) {
      let nowCurTime = percent * 0.01 * this.duration1;
      this.$store.commit("setChangeTime", nowCurTime);
    },

    //点击播放条切换播放进度
    updatemove(e) {
      if (!this.tag) {
        //进度条的坐标
        // offsetParent 盒子的左边距的距离（距离父盒子）
        let curLength = this.$refs.bg.offsetLeft;
        // console.log("curLength:" + curLength);
        this.progressLength = this.$refs.progress.getBoundingClientRect().width;
        let newPercent = ((e.clientX - curLength) / this.progressLength) * 100;
        if (newPercent > 100) {
          newPercent = 100;
        } else if (newPercent < 0) {
          newPercent = 0;
        }
        this.curLength = newPercent;
        this.changeTime(newPercent);
      }
    },

    //显示播放歌曲中的列表
    changeAside() {
      this.$store.commit("setShowAside", !this.showAside);
      // console.log("!this.showAside:" + this.showAside);
    },

    //上一首
    prev() {
      if (this.listIndex != -1 && this.listOfSongs.length > 1) {
        if (this.listIndex > 0) {
          //返回上一首
          this.$store.commit("setListIndex", this.listIndex - 1);
        } else {
          this.$store.commit("setListIndex", this.listOfSongs.length - 1);
        }
      }
      this.toplay1(this.listOfSongs[this.listIndex].url);
      // console.log(this.listIndex);
    },

    //下一首
    next() {
      if (this.listIndex != -1 && this.listOfSongs.length > 1) {
        if (this.listIndex < this.listOfSongs.length - 1) {
          this.$store.commit("setListIndex", this.listIndex + 1);
        } else {
          this.$store.commit("setListIndex", 0);
        }
      }
      this.toplay1(this.listOfSongs[this.listIndex].url);
    },

    //播放
    toplay1(url) {
      if (url && url != this.url) {
        this.$store.commit("setIsPlay", true);
        this.$store.commit("setListIndex", this.listIndex);
        this.$store.commit("setId", this.listOfSongs[this.listIndex].id);
        this.$store.commit("setUrl", this.$store.state.configure.HOST + url);
        this.$store.commit(
          "setPicUrl",
          this.$store.state.configure.HOST +
            this.listOfSongs[this.listIndex].pic
        );

        this.$store.commit(
          "setTitle",
          this.replaceFName(this.listOfSongs[this.listIndex].name)
        );
        this.$store.commit(
          "setArtist",
          this.replaceLName(this.listOfSongs[this.listIndex].name)
        );
        this.$store.commit(
          "setLyric",
          this.parseLyric(this.listOfSongs[this.listIndex].lyric)
        );
        // console.log(this.parseLyric(this.listOfSongs[this.listIndex].lyric));
        this.$store.commit("setIsActive", false);
        if (this.loginIn) {
          getCollectOfUserId(this.userId).then((res) => {
            console.log(res);
            for (let item of res) {
              if (item.songId == this.id) {
                this.$store.commit("setIsActive", true);
                break;
              }
            }
          });
        }
      }
    },

    //跳转歌词
    toLyric() {
      if (this.$route.path === "/lyric") {
        return; // 如果当前路由与目标路由相同，则不进行导航
      }
      this.$router.push({ path: `/lyric` });
    },

    //下载音乐
    download() {
      download(this.url)
        .then((res) => {
          let content = res.data; //res里的数据
          //创建一个 <a> 元素来模拟用户点击下载链接的行为
          let eleLink = document.createElement("a");
          //download 属性指定了当用户点击这个链接时，要下载的文件的名称
          eleLink.download = `${this.artist}-${this.title}.mp3`;
          eleLink.style.display = "none";
          //把字符内容转换成blob地址
          //Blob（Binary Large Object）是一种可以存储大量二进制数据的对象。
          //将content包装成数组
          let blob = new Blob([content]);
          // URL.createObjectURL(blob) 方法可以生成一个包含 Blob 对象数据的 URL，这个 URL 可以用于下载或展示文件内容
          eleLink.href = URL.createObjectURL(blob);
          //把链接地址加到document里
          document.body.appendChild(eleLink);
          // 当调用 click() 方法时，浏览器会自动触发元素的点击事件，从而开始下载对应的文件
          eleLink.click();
          //移除
          document.body.removeChild(eleLink);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    //收藏
    collection() {
      if (this.loginIn) {
        var params = new URLSearchParams();
        params.append("userId", this.userId);
        params.append("type", 0);
        params.append("songId", this.id);
        setCollect(params).then((res) => {
          if (res.code == 1) {
            this.$notify({
              title: "收藏成功",
              message: "",
              type: "success",
            });
            this.$store.commit("setIsActive", true);
          } else if (res.code == 2) {
            deleteCollect(this.userId, this.id)
              .then((res) => {
                if (res) {
                  this.$notify({
                    title: "移除该收藏成功",
                    message: "",
                    type: "success",
                  });
                  this.$store.commit("setIsActive", false);
                } else {
                  this.$notify({
                    title: "移除该收藏失败",
                    message: "",
                    type: "error",
                  });
                }
              })
              .catch((err) => {
                console.log(err);
                this.$notify({
                  title: "移除该收藏失败",
                  message: "",
                  type: "error",
                });
              });
          } else {
            this.$notify({
              title: "收藏失败",
              message: "",
              type: "error",
            });
          }
        });
      } else {
        alert("请先登录");
      }
    },
  },
};
</script>

<style>
@import "../assets/css/play-bar.css";
</style>
