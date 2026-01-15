<template>
  <div :class="cardClasses">
    <slot />
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  className: {
    type: String,
    default: ''
  }
})

const cardClasses = computed(() => {
  return `rounded-lg border-2 border-black bg-white ${props.className}`
})
</script>
