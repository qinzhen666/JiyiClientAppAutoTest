# JiyiClientAppAutoTest
即医客户端App自动化测试
yaml驱动说明
```yaml
methods:
  toSearchPage:
    steps:
      - element: edit_search_out     #定义：指定一个要操作的元素，不写具体操作的话默认就是click
      - scrollSelect: text-感冒灵     #定义：滚动查找并点击，可以text、id、AccessibilityId为查找条件(暂时仅实现了Android端)
                                     #用法：以-符号为分隔符，-前写定位的类型byType,-后写定位条件的值typeValue
      - element: edit_search_out
        send: $sendText              #定义：相当于sendKeys用法，使用$sendText表示为传参，具体参数由测试数据驱动传入

      - element: edit_search_out
        get: text                    #定义：get，获取元素属性值，text,class,id等；dump,属性值在map中的存储key
        dump: price                  #用法：get后填写需要获取的属性，dump中填写需要拿到获取结果的key值，在在BasePage中的HashMap"param"中获取到

      - element: edit_search_out
        finds: text(size)            #定义：相当于findElements
                                     #用法：参数text表示获取所有元素的text集合,可以通过key"findsTextList"在BasePage中的HashMap"attributeResult"中获取到；
                                          #参数size表示获取findElements得到的集合大小，可以通过key"size"在BasePage中的HashMap"attributeResult"中获取到
      - scroll: 3                    #设置滚动次数
        press:  1/2-2/3              #开始位置 -符号两边分别为x,y坐标值的占总size的比例，以分数形式填写
        moveTo: 1/2-1/3              #结束位置 -符号两边分别为x,y坐标值的占总size的比例，以分数形式填写

elements:
  edit_search_out:
    element:
      - id: edit_search_out
        os: android                  #定义：元素对应不同系统的定位符
        version: 1.0                 #定义：元素对应不同版本的定位符
      - id: edit_search_outIOS
        os: ios
        version: 1.1
      - xpath: //*[@text='aaa']      #定义同一个元素的不同定位方式，在一个方式失败后会尝试其他的定位方式
        os: ios
        version: 1.1
      - text: bbb
        os: android
        version: 1.0
```
