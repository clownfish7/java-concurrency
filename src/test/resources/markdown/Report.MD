#### 七大 BlockingQueue 总结

---

| BlockingQueue         | Bounded   | add                           | offer                                 | put           | remove    | poll  | element    | peek  | take   |
| :---:                 | :---:     | :---:                         | :---:                                 |:---:          |:---: |:---: |:---: |:---: |:---: |
| ArrayBlockingQueue    | Yes       | 成功 return true / 失败 抛出异常 | 成功 return true / 失败 return false   | 队列满会阻塞     |单元格 |单元格 |单元格 |单元格 |单元格 |
| PriorityBlockingQueue | No        | 单元格                         |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |
| LinkedBlockingQueue   | Optional  | 成功 return true / 失败 抛出异常 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |
| LinkedBlockingDeque   | Optional  | 单元格                         |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |
| SynchornousQueue      | Yes       | 成功 return true / 失败 抛出异常 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |
| DelayQueue            | No        | 单元格                         |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |
| LinkedTransferQueue   | No        | 尾部插入元素，永远返回 true       |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |单元格 |

**so ugly!? what the fuck man**