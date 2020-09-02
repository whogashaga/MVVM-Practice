# MVVM-Practice

```flow
st=>start: 設定要監聽的 Redmine URL 及其 table name
op0.5=>operation: 打 API 取得 issues list
op1=>operation: 連結 DB 取得 cursor
op2=>operation: 取得 or 創建 Redmine Issue Table
op3=>operation: 將新的 issue 寫入 table
op3.5=>operation: 根據新增的 issue，將 id 加入 list 存放, 等待打 detail API
op4=>operation: 有更新的 issue，將 id 加入 list 存放, 等待打 detail API
op5=>operation: 打 detail API 並將 detail object 依照格式存取至 Dictionary { "被指派的人":"被指派的issue list" }
op6=>operation: 將 Dictionary 內容 parse 成 Slack msg API 的 request 格式
op7=>operation: 將 新增項目及更新項目 Dictionary 內容合併成一包 Slack Request
cond1=>condition: 檢查 DB 是否存在
cond2=>condition: 檢查 DB 中是否有此 Table 
cond3=>condition: 檢查所有 remote issue 使否存在 Table 中
cond4=>condition: 比對 remote 與 local 的更新時間是否相同
cond5=>condition: 檢查是否為第一次創建 Table
sub1=>subroutine: create DB
sub2=>subroutine: create Table
e=>end: Slack Bot 傳送資訊
e2=>end: issue 沒被 update
e3=>end: continue for loop
e4=>end: 發送 Slack Messege 通知大家
st->op0.5->cond1->op1->cond2
cond1(yes)->op1
cond1(no)->sub1->op1
cond2(yes)->cond3
cond2(no)->sub2->cond3
cond3(yes)->cond4
cond3(no)->op3->op3.5->cond5
cond4(yes)->e2
cond4(no)->op4->cond5
cond5(yes)->e3
cond5(no)->op5->op6->e4
```
