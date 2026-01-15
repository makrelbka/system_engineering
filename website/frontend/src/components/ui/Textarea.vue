<template>
  <textarea
    :id="id"
    :value="modelValue"
    :placeholder="placeholder"
    :required="required"
    :class="textareaClasses"
    @input="$emit('update:modelValue', $event.target.value)"
  />
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  id: String,
  modelValue: {
    type: String,
    default: ''
  },
  placeholder: String,
  required: Boolean,
  className: {
    type: String,
    default: ''
  }
})

defineEmits(['update:modelValue'])

const textareaClasses = computed(() => {
  return `flex min-h-[100px] w-full rounded-md border-2 border-black bg-white px-3 py-2 text-sm transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-black focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 resize-none ${props.className}`
})
</script>
