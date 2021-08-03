export default function(config){
  return new Promise((resolve,reject) => {
    wx.request({
      url: config.url,
      method:config.method || 'get',
      data:config.data || [],
      success: res => {
        resolve(res);
      },
      fail: err => {
        reject(err);
      }
    })    
  }) 
}