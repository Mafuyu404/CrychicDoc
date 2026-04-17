---
layout: doc
title: 词缀定义格式
---

# 词缀定义格式

词缀定义文件是[词缀](affix.md)(Affix)在数据包中的数据驱动定义文件。

## 定义格式

词缀在游戏内使用`AFFIX`注册表，数据包路径为champions/affix，即所有词缀定义文件都需要在data/\<命名空间\>/champions/affix目录内定义，词缀标签则需要在data/\<命名空间\>/tags/champions/affix目录内定义。

词缀定义文件使用JSON格式，并具有下列结构：
<LiteTree>
{} JSON文件根对象
    description：文本组件，提示框中显示的词缀名称
    effects：构成该词缀的词缀效果组件
    exclusive_set：（默认为空）词缀的排斥集，指定与此词缀不兼容的的词缀，可以为一个词缀ID，一个词缀标签，或一个词缀id构成的数组
    min_cost：词缀的最小修正等级
    max_cost：词缀的最大修正等级
</LiteTree>

例如，以下为模组数据包中magnetic.json（磁力）词缀文件的内容。

```js
{
  "description": {
    "translate": "affix.champions.magnetic"
  },
  "effects": {
    "champions:target": [
      {
        "effect": {
          "type": "champions:movement",
          "speed": 0.05
        },
        "requirements": {
          "condition": "minecraft:entity_properties",
          "entity": "this",
          "predicate": {
            "distance": {
              "absolute": {
                "max": 5.0
              }
            }
          }
        }
      }
    ]
  },
  "max_cost": {
    "base": 5,
    "per_level_above_first": 5
  },
  "max_level": 1,
  "min_cost": {
    "base": 3,
    "per_level_above_first": 3
  }
}
```

## 定义行为

词缀定义数据仅在服务端启动时被加载一次，使用`/reload`命令不可以使词缀定义文件被重新加载，而必须重启服务端。

## 修正词缀等级范围

<LiteTree>
{} 根节点
    base：词缀为I级时的获取等级
    per_level_above_first：词缀每增加1级，获取等级增长的数值
</LiteTree>

如果一个词缀在某个等级时最大修正等级小于最小修正等级，则这一等级的词缀无法通过自然生成的方式附加到生物上。

## 词缀效果组件

词缀所产生的实际影响主要由若干个**词缀效果组件**控制。词缀效果组件可以修改一些数值，或在特定条件下触发若干个**词缀效果**。

以下列出其数据格式。

### 值效果型组件

**值效果型**的词缀效果组件是对生物行为中某些数值进行修改的组件，其命名空间ID表示其修改的数值，如`champions:damage`表示其修改的值为伤害值。

大部分值效果组件所修改的数值在某些行为发生时才发挥作用，例如`champions:damage`在生物攻击时才生效，而不是生物获得此词缀时。

#### 普通值效果型

`普通值效果型`组件的声明必须为一个值效果。

<LiteTree>
{} 根节点
    该值效果的子标签
</LiteTree>

暂无普通值效果型组件。

#### 带谓词的值效果型

<LiteTree>
[] 带谓词的值效果型组件命名空间ID，该组件可声明多个值效果，每个值效果都可以设定一个谓词充当该效果的触发条件
    {} effect：一个值效果
        该值效果的子标签
    {} requirements：此效果生效需满足的谓词，不能引用单独的谓词文件，各组件使用的战利品表上下文在下方表格列出，
</LiteTree>

带谓词的值效果型组件如下表：

| 命名空间ID | 修改的数值 | 战利品上下文 |
| --- | --- | --- |
| champions:damage_protection | 伤害减免 | this_entity: 受伤或死亡的实体 origin：受伤或死亡的实体位置 damage_source：伤害来源 direct_attacking_entity：伤害的直接实体 attacking_entity：伤害的源发实体 |

