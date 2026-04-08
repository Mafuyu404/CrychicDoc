---
authors: ['Gu-meng']
---
# KubeJS Additions Event Overview
KubeJS Additions (called "KubeJS Additions" below) provides many extra events.  
They are listed below.
## Event List
|    Main Event     |             Sub Event             |   Folder    |           Purpose            | Example | Tested |
| :---------------: | :-------------------------------: | :-------------: | :-------------------: | :---: | :----------: |
|  JEIAddedEvents   |      registerRecipeCatalysts      | client_scripts  | Register JEI recipe catalysts |   ~   |   ~    |
|  JEIAddedEvents   |          registerRecipes          | client_scripts  | Register JEI recipes |   ~   |   ~    |
|  JEIAddedEvents   | registerVanillaCategoryExtensions | client_scripts  | Register vanilla category extensions? |   ~   |   ~    |
|  JEIAddedEvents   |        registerIngredients        | client_scripts  | Register ingredients? |   ~   |   ~    |
|  JEIAddedEvents   |        onRuntimeAvailable         | client_scripts  | Runtime availability hook? |   ~   |   ~    |
|  JEIAddedEvents   |        registerGUIHandlers        | client_scripts  | Register GUI handlers |   ~   |   ~    |
|  JEIAddedEvents   |       registerItemSubtypes        | client_scripts  | Register item subtypes |   ~   |   ~    |
|  JEIAddedEvents   |         registerAdvanced          | client_scripts  | Advanced registration? |   ~   |   ~    |
|  JEIAddedEvents   |       registerFluidSubtypes       | client_scripts  | Register fluid subtypes |   ~   |   ~    |
|  JEIAddedEvents   |        registerCategories         | client_scripts  | Register categories? |   ~   |   ~    |
|  JEIAddedEvents   |  registerRecipeTransferHandlers   | client_scripts  | Register recipe transfer handlers? |   ~   |   ~    |
|    JadeEvents     |       onCommonRegistration        | startup_scripts |           ~           |   ~   |      ~       |
|    JadeEvents     |       onClientRegistration        | client_scripts  |           ~           |   ~   |      ~       |
|    ArchEvents     |             registry              | startup_scripts | Arch registry event |   ~   |   ~    |
|    ArchEvents     |           handleStartup           | startup_scripts | Arch startup-side event |   ~   |   ~    |
|    ArchEvents     |           handleServer            | server_scripts  | Arch server-side event |   ~   |   ~    |
|    ArchEvents     |           handleClient            | client_scripts  | Arch client-side event |   ~   |   ~    |
| CommonAddedEvents |            entityTame             | server_scripts  | Entity taming event |   ~   |   √    |
| CommonAddedEvents |            playerClone            | server_scripts  | Player clone event |   ~   |   X    |
| CommonAddedEvents |         entityEnterChunk          | server_scripts  | Entity enters chunk event |   ~   |   X    |
| CommonAddedEvents |           playerRespawn           | server_scripts  | Player respawn event |   ~   |   √    |
| CommonAddedEvents |       playerChangeDimension       | server_scripts  | Player dimension change event |   ~   |   √    |

> Note: The "Tested" column may be inaccurate. Use it as reference only and verify in your own environment.
