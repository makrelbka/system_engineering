<template>
  <input
    :id="id"
    :type="type"
    :value="modelValue"
    :placeholder="placeholder"
    :required="required"
    :class="inputClasses"
    @input="$emit('update:modelValue', $event.target.value)"
  />
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  id: String,
  type: {
    type: String,
    default: 'text'
  },
  modelValue: {
    type: [String, Number],
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

const inputClasses = computed(() => {
  return `flex h-9 w-full rounded-md border-2 border-black bg-white px-3 py-1 text-sm transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-black focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 ${props.className}`
})
</script>
