
// pages/register/register.js
Page({

  data: {
    depList:['小a','小b','小c','小d'],
    secDepList:['小a','小b','小c','小d'],
    depIndex:0,
    secIndex:0,
    sex:0,
    isLoged:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const isLoged = wx.getStorageSync("userInfo");
    this.setData({
      isLoged:isLoged
    })
    if(!isLoged){
      console.log("用户还没登陆");
    }else{
      console.log(isLoged);
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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

  bindPickerChange(event){
    this.setData({
      depIndex:event.detail.value
    })
  },
  bindPickerChangeSec(event){
    this.setData({
      secIndex:event.detail.value
    })
  },
  sexChange(){
    this.setData({
      sex:!this.data.sex
    })
  }
})