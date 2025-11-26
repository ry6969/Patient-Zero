# CHANGELOG

## [v1.1] - 2025-11-26

### CHANGES
- **StoryNode**: 
	- Ported the story from "save point 2"
	- Separated dynamic text into static nodes for better maintainability
	  (allows game logic to remain in GameEngine while story data stays clean)

- **Folder Structure**:
```
Patient Zero/
├── CHANGELOG.md
├── README.md
├── STORY_REFERENCE.md
└── src/
    ├── Main.java
    ├── condition/     (empty, ready for future use)
    ├── effect/        (empty, ready for future use)
    ├── engine/
    │   ├── GameEngine.java
    │   └── StoryData.java
    ├── model/
    │   ├── Choice.java
    │   ├── Player.java
    │   └── StoryNode.java
    └── ui/
        └── TextRenderer.java
```
	
### Work in Progress
- Conditions interface and its implementations
- Effect interface and its implementations
- **GameEngine**: Game loop and logic still in progress
- **README**: Only posted the template, overview and other description in progress *[Features may still be revised depending on time constraints and project adjustments]*

### Note to Devs
- *To mimic the dynamic text from "save point 2" while separating different areas of concern, I have turned all dynamic text into static text nodes in StoryNode. The mechanism for triggering dynamic node output can be applied in the GameEngine or a separate class or subclass of the aforementioned class.*
