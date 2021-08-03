// pages/home/home.js
import request from "../../services/network/request.js"

Page({
  data: {
    // indicatorDots: true,
    // vertical: false,
    // autoplay: true,
    // interval: 3000,
    // duration: 1000,
    banners:[], //轮播图数组
    list:[
      {
        title:"网宣部",
        introduce:"网宣部是电协对外日常宣传的一个端口，万千技能集一身，统筹着协会的各项宣传工作。主要负责运营协会的微信公众平台，将协会期望表达的东西传递给大家，同时运用Ps、Pr、Ae，制作活动海报、宣传单、宣传视频等。"
      },
      {
        title:"秘书部",
        introduce:"秘书部在日常中主要负责电协会员课的课室申请；比赛物资的准备；协会内部会议流程的记录；配合网宣部提供新闻稿、通讯稿的文案；还有负责协会论坛的监督。在招新期间负责招新摊位的申请，公共横幅的申请。年末负责社团的年审与红旗社团评比。"
      },
      {
        title:"外联部",
        introduce:"外联部主要负责策划主办各种大会和内建活动，包括策划案的编写，活动组织，选择主持人；对接赞助商，供应商，确保各项活动物资与资金得到保障；与老师进行项目对接，及时为电协人提供各项目的信息；负责加强协会与国内其它高校社团间的密切合作与联系。"
      },
      {
        title:"项目部",
        introduce:"项目部的工作主要有协会每年众多项目申请的审核；有各类比赛时赛前赛后的资料收集；协会存放在不同的平台的大量资源的管理，还肩负着管理协会平台的重任。"
      },
      {
        title:"实践部",
        introduce:"实践部负责的工作主要有协会会员工具的采购与分发；部分科技比赛及其流程的策划与安排；部分摆摊的人员安排和策划以及协助开课组做好会员课开课的准备。"
      },
      {
        title:"维修部",
        introduce:"维修部的日常工作是维护电协元件库，将摆放不整齐的元器件归位；组织校内外义修，以及修理学校驿站收集的故障家电；进行家电维修的培训。"
      }
    ], //部门数据
    depTop:0,
    showOneButtonDialog: false,
    buttons: [{text: '确定'}],
  },



  onLoad: function (options) {
    wx.setStorageSync("userInfo","Tan");
    request({
      url:"http://152.136.185.210:7878/api/m5/home/multidata"
    }).then(res => {
      this.setData({
        banners:res.data.data.banner.list
      })
    }).catch(err => {
      console.log(err)
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    const query = wx.createSelectorQuery();
    query.select('#dep').boundingClientRect();
    query.selectViewport().scrollOffset();
    query.exec(res => {
      // this.changeTop(res[0].top);
      this.setData({
        depTop:res[0].top
      })
      //res[1].scrollTop // 显示区域的竖直滚动位置
    });
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  showDate(){
    console.log(this.data)
  },
  scrollToDep(){
    wx.pageScrollTo({
      duration: 500,
      scrollTop:this.data.depTop
    })
  },
  joinDialog(){
    this.setData({
      showOneButtonDialog: !this.data.showOneButtonDialog,
    })
  }
})