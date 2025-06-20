/**
 * Content utilities for CryChicDoc
 * Word counting, text processing, and content parsing
 */

import { countWord } from '../functions';

/**
 * Text processing utilities
 */
export const text = {
  /** Count words in text with multi-language support */
  countWord,
  
  /** Calculate reading time based on word count */
  getReadingTime: (text: string, wordsPerMinute: number = 200): number => {
    const wordCount = countWord(text);
    return Math.ceil(wordCount / wordsPerMinute);
  },
};


/**
 * Main content utilities export
 */
export const contentUtils = {
  text,
  
  // Direct access
  countWord,
  getReadingTime: text.getReadingTime,
};

export default contentUtils; 